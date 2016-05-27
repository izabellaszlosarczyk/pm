/**
 * Created by arkadiusz on 15.04.16.
 */
export default class RegisterController {
    constructor(usersService, $state) {
        this.usersService = usersService;
        this.$state = $state;
        this.err = '';
        this.indexNumber = '';
        this.name = '';
        this.surname = '';
        this.academicYear = '';
        this.email = '';
        this.password = '';
        this.repeatedPassword = '';
    }

    validate() {
        if (this.indexNumber == undefined ||
            this.name == undefined ||
            this.surname == undefined ||
            this.academicYear == undefined ||
            this.email == undefined ||
            this.password == undefined ||
            this.repeatedPassword == undefined) {

            this.err = 'WRONG_DATA';
            return false;
        }

        if (this.indexNumber.length == 0 ||
            this.name.length == 0 ||
            this.surname.length == 0 ||
            this.academicYear.length == 0 ||
            this.email.length == 0 ||
            this.password.length == 0 ||
            this.repeatedPassword.length == 0) {

            this.err = 'WRONG_DATA';
            return false;
        }

        if (this.password != this.repeatedPassword) {
            this.err = 'WRONG_PASSWORD';
            return false;
        }

        this.err = '';
        return true;
    }

    register() {
        let registrationData = {
            indexNumber: this.indexNumber,
            name: this.name,
            surname: this.surname,
            academicYear: this.academicYear,
            email: this.email,
            password: this.password
        };
        if (this.validate()) {
            this.usersService.register(registrationData).then(successResponse => {
                this.$state.go('login')
            }, errorResponse => {
                this.err = 'USER_EXIST';
            });
        }
    }
}
