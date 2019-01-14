class BaseServer {

    constructor(private readonly method: string = `GET`,
                private readonly baseUrl: string = `/webapi/`) {
    }

    protected async getObject<T>(urlPart: string): Promise<T> {
        return (await fetch(this.baseUrl + urlPart, { credentials: "same-origin" })).json()
            .catch(status => Error(`JSON didn't load successfully; error code: ${status}`));
    }
}
