package com.epam.jdbc;

import com.epam.fp.CheckedConsumer;
import com.epam.fp.CheckedFunction1;
import com.epam.fp.CheckedRunnable;
import com.epam.fp.StreamUtils;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@SuppressWarnings("WeakerAccess")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class JdbcUtils {

  @NotNull
  public <T> Iterator<T> toIterator(@NotNull ResultSet rs,
                                    @NotNull CheckedFunction1<ResultSet, T> rowMapper) {
    return new Iterator<>() {
      @Override
      @SneakyThrows
      public boolean hasNext() {
        return !rs.isLast();
      }

      @Override
      @SneakyThrows
      public T next() {
        if (rs.next())
          return rowMapper.apply(rs);
        throw new NoSuchElementException();
      }
    };
  }

  @NotNull
  @SneakyThrows
  @Contract(pure = true)
  public <T> Stream<T> toStream(@NotNull ResultSet resultSet,
                                @NotNull CheckedFunction1<ResultSet, T> rowMapper) {

    val statement = resultSet.getStatement();
    val connection = statement.getConnection();

    return StreamUtils.streamOf(toIterator(resultSet, rowMapper))
      .onClose(CheckedRunnable.of(() -> {
        //noinspection EmptyTryBlock
        try (connection; statement; resultSet) {
        }
      }).unchecked());
  }

  public <T, R> R mapStream(@NotNull ResultSet resultSet,
                            @NotNull CheckedFunction1<ResultSet, T> rowMapper,
                            @NotNull CheckedFunction1<Stream<T>, R> streamMapper) {

    return StreamUtils.mapStream(toStream(resultSet, rowMapper), streamMapper);
  }

  public <T> void withStream(@NotNull ResultSet resultSet,
                             @NotNull CheckedFunction1<ResultSet, T> rowMapper,
                             @NotNull CheckedConsumer<Stream<T>> checkedConsumer) {

    StreamUtils.withStream(toStream(resultSet, rowMapper), checkedConsumer);
  }
}
