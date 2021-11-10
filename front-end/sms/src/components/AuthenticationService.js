import Axios from "axios";
import {API_URL} from '../../Constants'
export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

class AuthenticationService {
    //Basic Auth
    executeBasicAuthenticationService(username, password) {
        return Axios.get(`${API_URL}/basicauth`, 
            {headers: {authorization: this.createBasicAuthToken(username, password)}})
    }
    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    registerSuccessfulLogin(username, password){
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username);
        this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
    }

    //Jwt Auth
    executeJwtAuthenticationService(username, password) {
        return Axios.post(`${API_URL}/authenticate`, {
            username,
            password
        })
    }
    createJwtToken(token) {
        return 'Bearer ' + token
    }
    registerSuccessfulLoginForJwt(username, token){
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username);
        this.setupAxiosInterceptors(this.createJwtToken(token))
    }


    //Utils
    logout(){
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    }

    isUserLoggedIn(){
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if(user==null) return false
            return true
    }

    getLoggedInUserName() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if(user == null) return ''
        return user
    }

    setupAxiosInterceptors(token) {
       
        Axios.interceptors.request.use(
            (config) => {
                if(this.isUserLoggedIn()) {
                    config.headers.authorization = token
                }
                return config
            }
        )
    }
}
export default new AuthenticationService();