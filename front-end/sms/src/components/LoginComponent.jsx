import React, {Component} from 'react'
import AuthenticationService from './AuthenticationService.js'


class LoginComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            // username : 'todo app',
            // password : '',
            phone : '',
            sms: '',
            hasLogFailed : false,
            showSuccessMessage : false
        }
        // this.handlerUsernameChange = this.handlerUsernameChange.bind(this)
        // this.handlerPasswordChange = this.handlerPasswordChange.bind(this)
        this.handlerChange = this.handlerChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
        this.sendCodeClicked = this.sendCodeClicked(this);
    }

    handlerChange(event){
        this.setState({[event.target.name]: event.target.value }
    )}
    sendCodeClicked() {

    }
    // handlerUsernameChange(event){
    //     this.setState({
    //         username : event.target.value
    //     })
    // }

    // handlerPasswordChange(event){
    //     this.setState({
    //         password : event.target.value
    //     })
    // }
    
    loginClicked(){
        // Hard Coded Service
        if(this.state.phone === '123' && this.state.password ===  '123'){
            AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password)
            this.props.history.push(`/welcome/${this.state.username}`)
            // this.setState({
            //     showSuccessMessage : true,
            //     hasLogFailed : false
            // })
        } 
        else{
            this.setState({
                hasLogFailed : true,   
                showSuccessMessage : false
            })
        }

        //Basic Authentication
        // AuthenticationService
        // .executeBasicAuthenticationService(this.state.username, this.state.password)
        // .then(() => {
        //         AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password)
        //         this.props.history.push(`/welcome/${this.state.username}`)
        // }).catch( () => {
        //     this.setState({
        //         hasLogFailed : true,   
        //         showSuccessMessage : false
        //     })
        // })

        //JWT Authentication
        // AuthenticationService
        // .executeJwtAuthenticationService(this.state.username, this.state.password)
        // .then((response) => {
        //         AuthenticationService.registerSuccessfulLoginForJwt(this.state.username, response.data.token)
        //         this.props.history.push(`/welcome/${this.state.username}`)
        // }).catch( () => {
        //     this.setState({
        //         hasLogFailed : true,   
        //         showSuccessMessage : false
        //     })
        // })

    }
 
    render() {
        return (
            <div>
                {/* controlled  component*/}
                {/* <ShowInvalidCredentials hasLogFailed={this.state.hasLogFailed}/> */}
                <h1>Login</h1>
                <div className="container">
                    {this.state.hasLogFailed && <div className="alert alert-warning">验证失败</div>}
                    {this.state.showSuccessMessage && <div>登录成功</div>}
                    {/* <ShowSuccessMessage showSuccessMessage={this.state.showSuccessMessage}/> */}
                    手机: <input type="text" name="phone" value={this.state.phone} onChange={this.handlerChange}/>
                    验证码: <input type="text" name="sms" value={this.state.sms} onChange={this.handlerChange}/>
                    <button className="btn btn-success" onClick={this.sendCodeClicked}>发送验证码</button>
                    <button className="btn btn-success" onClick={this.loginClicked}>登录</button>
                </div>
            </div>
        )  
    }
    
}

export default LoginComponent;