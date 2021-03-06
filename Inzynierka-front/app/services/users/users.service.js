class UserService {
    /*@ngInject*/

    constructor($http, pmServerUrl, AuthorizationToken, $base64, $sessionStorage, $state) {
        this.$http = $http;
        this.baseUrl = pmServerUrl;
        this.base64 = $base64;
        this.$storage = $sessionStorage;
        this.userSessionData = {
            'userId' : '',
            'token' : AuthorizationToken.NO_AUTH
        };
        this.userData = {};
        this.requestedFileDetails = {};
        this.stateProvider = $state;
        this.analysesType;
        this.jsonToVisualisation;
    }

    getUrl(url) {
        return `${this.baseUrl}/${url}`;
    }

    login(loginData) {
        const basicAuth = 'Basic ' + this.base64.encode(loginData.email + ':' + loginData.password);
        return this.$http({
            url: this.getUrl('user/info'),
            method: "GET",
            headers: {
                'Authorization': basicAuth
            }
        });
    }
    addRequestedFileDetails(file){
        this.requestedFileDetails = file;
    }

    getFileDetails(fileName){
        return this.$http({
            url: this.getUrl(`file/loadDetails/${fileName}`),
            method: "GET"
        });

    }

    getFilesDetails(filesNames){
        return this.$http({
            url: this.getUrl(`file/loadDetails/files`),
            method: "POST",
            data: filesNames
        });

    }

    getUserFiles(userId) {
        return this.$http({
            url: this.getUrl(`files/users/${userId}`),
            method: "GET"
        });
    }
    getFile(fileName) {
        return this.$http({
            url: this.getUrl(`file/loadFile`),
            method: "POST",
            data: fileName,
            responseType: 'arraybuffer'
        });
    }


    getNewsFiles(userEmail) {
        return this.$http({
            url: this.getUrl(`mainContent/news`),
            method: "POST",
            data: userEmail
        });
    }

    addOpinionToFile(scoreReq){
        let request = {
            file: scoreReq.fileName,
            score: scoreReq.score
        };
        return this.$http({
            url: this.getUrl(`rate/score`),
            method: "POST",
            data: request
        });
    }
    addCommentToFile(commentReq){
        let request = {
            file: commentReq.fileName,
            comment: "~" + this.userData.email + ": " + commentReq.comment
        };
        return this.$http({
            url: this.getUrl(`rate/comment`),
            method: "POST",
            data: request
        });
    }

    getUserData(loginData){
        return this.$http({
            url: this.getUrl('edit/getAfterLogin'),
            method: "POST",
            data: loginData
        });
    }

    getUserImage(fileName){
        return this.$http({
            url: this.getUrl('content/' + fileName),
            method: "GET"
        });
    }
    changeTimeOfLog(email){
        return this.$http({
            url: this.getUrl('edit/editTime/' + email),
            method: "GET"
        });
    }

    saveNewImage(userFile, fileName){ //potem to zmienie, ogolna metoda na dodawnaie plikow
       var formdata = new FormData();
        formdata.append( 'name', fileName);
        formdata.append( 'file', userFile);
        formdata.append( 'type', 'profilePic/jpg');
        return this.$http({
            url: this.getUrl('file/saveNew'),
            method: "POST",
            data: formdata,
            cache: false,
            headers: {'Content-Type': undefined },
            processData: false
        });

    }

    loadFiles(){
        return this.$http({
            url: this.getUrl(`/mainContent/allDetails`),
            method: "GET"
        });
    }

    saveNewFile(userFile, fileName, type){ 
        var formdata = new FormData();
        formdata.append( 'name', fileName);
        formdata.append( 'file', userFile);
        formdata.append( 'type', type);
        return this.$http({
            url: this.getUrl('file/saveNew'),
            method: "POST",
            data: formdata,
            cache: false,
            headers: {'Content-Type': undefined },
            processData: false
        });

    }
    saveNewEntity(userFile, fileName, type){ //potem to zmienie, ogolna metoda na dodawnaie plikow
        var formdata = new FormData();
        formdata.append( 'name', fileName);
        formdata.append( 'file', userFile);
        formdata.append( 'type', type);
        return this.$http({
            url: this.getUrl('file/saveNewEntity'),
            method: "POST",
            data: formdata,
            cache: false,
            headers: {'Content-Type': undefined },
            processData: false
        });

    }

    saveNewFileBackend(userFileData, userFileDesc, fileType, analysesType, nameOfOutputFile, email){ 
        var formdata = new FormData();
        formdata.append( 'nameData', userFileData.name);
        formdata.append( 'fileData', userFileData);
        formdata.append( 'nameDesc', userFileDesc.name);
        formdata.append( 'fileDesc', userFileDesc);
        formdata.append( 'type', fileType);
        formdata.append( 'analysesType', analysesType);
        formdata.append('name', nameOfOutputFile);
        formdata.append( 'email', email);
        return this.$http({
            url: this.getUrl('file/uploadNew'),
            method: "POST",
            data: formdata,
            cache: false,
            headers: {'Content-Type': undefined },
            processData: false
        });


    }
    
    
    addFileEntity(file){
        return this.$http({
            url: this.getUrl(`file/addFileDetails`),
            method: "POST",
            data: file
        });
    }

    logout() {
        return this.$http({
            url: this.getUrl('logout'),
            method: "POST"
        });
    }

    register(userData) {
        return this.$http({
            url: this.getUrl('register/new'),
            method: "POST",
            data: userData
        });
    }

    edit(userData) {
        return this.$http({
            url: this.getUrl(`edit/saveEdited`),
            method: "POST",
            data: userData
        });
    }


    editPhoto(userData) {
        return this.$http({
            url: this.getUrl(`edit/saveEditedPhoto`),
            method: "POST",
            data: userData
        });
    }

    getUser(userId) {
        return this.$http({
            url: this.getUrl(`users/${userId}`),
            method: "GET"
        });
    }
    deleteUser(userId) {
        return this.$http({
            url: this.getUrl(`users/${userId}/delete`),
            method: "DELETE"
        });
    }

   deleteFileFromSubs(deleteRequest) {
        return this.$http({
            url: this.getUrl(`file/deleteFromSubs`),
            method: "POST",
            data: deleteRequest
        });
    }

    addFileToSubs(addRequest) {
        return this.$http({
            url: this.getUrl(`file/addSubs`),
            method: "POST",
            data: addRequest
        });
    }


    getRandomFileName(){
        return this.$http({
            url: this.getUrl(`mainContent/randomName`),
            method: "GET"
        });
    }

    getRandomFile(){
        return this.$http({
            url: this.getUrl(`mainContent/randomFile`),
            method: "GET"
        });
    }


    setUserSessionData(userSessionData){
        this.userSessionData = userSessionData;
        this.$storage.userSessionData = this.userSessionData;
        this.$storage.logged = true;
    }

    getUserSessionData(){
        if (!this.userSessionData && this.$storage.userSessionData) {
            this.userSessionData = this.$storage.userSessionData;
        }

        return this.userSessionData;
    }
    removeUserSessionData() {
        delete this.userSessionData;
        delete this.$storage.userSessionData;
        delete this.$storage.userData;
        this.$storage.logged = false;
        this.stateProvider.go('login');
    }

    getUserDataValues(){
        if (this.$storage.userData) {
            this.userData = this.$storage.userData;
        }
        return this.userData;
    }

    setUserDataValues(userData){
        this.userData = userData;
        this.$storage.userData = this.userData;
        this.$storage.logged = true;
    }

    removeUserDataValues(){
        delete this.userData;
        delete this.$storage.userData;
    }




}

export default UserService;
