import Axios from "axios";
export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
Axios.defaults.withCredentials=true
const qs = require('qs')
class AuthenticationService {

    executeSmsGetCode(phone, imgCode) {
         return Axios.get(`http://localhost:8080/auth/sms`, {params: {phone, imgCode}})
    }

    executeSmsAuthenticationService(phone, sms) {
        let postData = qs.stringify({phone, sms})
        return Axios.post(`http://localhost:8080/auth/login`, postData)
    }

    executeImageCodeGet() {
        return Axios.get('http://localhost:8080/auth/getImage/base64')
    }

    // //Utils
    logout(){
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    }

    isUserLoggedIn(){
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if(user==null) return false
            return true
    }

    registerSuccessfulLogin(phone){
            sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, phone);
            // this.setupAxiosInterceptors(this.createJwtToken(token))
        }


    
    // getLoggedInUserName() {
    //     let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    //     if(user == null) return ''
    //     return user
    // }

    // setupAxiosInterceptors(token) {
       
    //     Axios.interceptors.request.use(
    //         (config) => {
    //             if(this.isUserLoggedIn()) {
    //                 config.headers.authorization = token
    //             }
    //             return config
    //         }
    //     )
    // }
}
export default new AuthenticationService();