class TokenService {
    /*@ngInject*/
    constructor($sessionStorage) {
        this.token = null;
        this.storage = $sessionStorage;
    }

    setToken(token) {
        this.token = token;
        this.storage.token = this.token;
    }

    getToken() {
        if (!this.token && this.storage.token) {
            this.token = this.storage.token;
        }
        return this.token;
    }

    removeToken() {
        delete this.token;
        delete this.storage.token;
    }
}

export default TokenService;
