class TokenService {
    /*@ngInject*/
    constructor() {
        this.token = null;
    }

    setToken(token) {
        this.token = token;
    }

    removeToken() {
        delete this.token;
    }
}

export default TokenService;
