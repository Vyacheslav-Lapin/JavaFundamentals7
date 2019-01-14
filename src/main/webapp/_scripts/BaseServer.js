// noinspection ThisExpressionReferencesGlobalObjectJS
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
class BaseServer {
    constructor(method = `GET`, baseUrl = `/webapi/`) {
        this.method = method;
        this.baseUrl = baseUrl;
    }
    getObject(urlPart) {
        return __awaiter(this, void 0, void 0, function* () {
            return (yield fetch(this.baseUrl + urlPart, { credentials: "same-origin" })).json()
                .catch(status => Error(`JSON didn't load successfully; error code: ${status}`));
        });
    }
}
//# sourceMappingURL=BaseServer.js.map
