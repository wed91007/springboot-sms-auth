import React, {Component} from 'react'
import AuthenticationService from './AuthenticationService.js'
import Axios from "axios";

class LoginComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            // username : 'todo app',
            // password : '',
            phone : '',
            sms: '',
            imgCode: '',
            imgBase64: '',
            imgCodeFailed: false,
            phoneValidated: false,
            smsValidated: false,
            smsSendSuccess : false,
            smsSendSuccessError : false,
            hasLogFailed : false,
            showSuccessMessage : false,
            repeatedClick: false
        }
        
        this.phoneHandlerChange = this.phoneHandlerChange.bind(this)
        this.smsHandlerChange = this.smsHandlerChange.bind(this)
        this.imgCodeHandlerChange = this.imgCodeHandlerChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
        this.sendCodeClicked = this.sendCodeClicked.bind(this)
        this.clearStatus = this.clearStatus.bind(this)
        this.refreshImageCode = this.refreshImageCode.bind(this)
    }

    componentDidMount() {
        this.refreshImageCode();
      }

      refreshImageCode() {
        Axios.get('http://localhost:8080/auth/getImage/base64'
        )
        .then(response => {
          this.setState({ imgBase64: response.data.url});
        });
      }


    phoneHandlerChange(event) {
        this.setState({[event.target.name]: event.target.value })
        const reg = /^1([0-9]*)?$/;		// 以数字1开头，任意数字结尾，且中间出现零个或多个数字
        if ((reg.test(event.target.value) && event.target.value.length === 11)) {
            this.setState({phoneValidated: true})
            console.log("phone is valid")
        }
        else(this.setState({phoneValidated: false}))
    }

    smsHandlerChange(event) {
        this.setState({[event.target.name]: event.target.value })
        const reg = /^\d*?$/;
        if ((reg.test(event.target.value) && event.target.value.length === 6)) {
            this.setState({smsValidated: true})
            console.log("sms is valid")
        }
        else(this.setState({smsValidated: false}))
    }

    imgCodeHandlerChange(event) {
        this.setState({[event.target.name]: event.target.value })
        const reg = /^\d*?$/;
        if ((reg.test(event.target.value) && event.target.value.length === 4)) {
            console.log("imgCode is valid")
        }
    }



    clearStatus() {
        this.setState({
            smsSendSuccess : false,
            smsSendSuccessError : false,
            hasLogFailed : false,
            showSuccessMessage : false,
            repeatedClick: false
        })
 
     
        
    }

    sendCodeClicked() {
        console.log("send code clicked")
        
        if(this.state.phoneValidated) {
            this.clearStatus()
            AuthenticationService
            .executeSmsGetCode(this.state.phone, this.state.imgCode)
            .then( (response) => {
                if(response.data.status === "img code error"){
                    this.setState({imgCodeFailed: true})
                }


                if(response.data.status === "repeated"){
                    this.setState({repeatedClick: true})
                }
                this.setState({smsSendSuccess: true})
            })
            .catch( (response) => {
                this.setState({smsSendSuccessError : true})
                console.log(response)
            })
        }
        else {
            
            this.setState({
                smsSendSuccessError: true,
                smsSendSuccess: false
            })
        }
            
        this.refreshImageCode()
        
    }
    
    
    loginClicked(){
        this.clearStatus()
        console.log("login clicked")
        AuthenticationService
        .executeSmsAuthenticationService(this.state.phone, this.state.sms)
        .then(() => {
                // console.log("response back" + response.data.status)
                AuthenticationService.registerSuccessfulLogin(this.state.phone)
                this.props.history.push(`/welcome/${this.state.phone}`)
                
                
                
        }).catch( () => {
            this.setState({
                hasLogFailed : true,   
                showSuccessMessage : false
            })
        })
    }
 
    render() {
        return (
            <div>
                {/* controlled  component*/}
                {/* <ShowInvalidCredentials hasLogFailed={this.state.hasLogFailed}/> */}
                <h1>Login</h1>
                <div className="container">
                    {this.state.repeatedClick && <div className="alert alert-warning">请求过于频繁</div>}  
                    {this.state.smsSendSuccess && <div className="alert alert-warning">验证码已发送</div>}
                    {this.state.smsSendSuccessError && <div className="alert alert-warning">验证码发送失败</div>}
                    {this.state.hasLogFailed && <div className="alert alert-warning">登陆失败</div>}
                    {this.state.showSuccessMessage && <div>登录成功</div>}
                    手机: <input type="text" name="phone" value={this.state.phone} onChange={this.phoneHandlerChange}/>
                    验证码: <input type="text" name="sms" value={this.state.sms} onChange={this.smsHandlerChange}/>
                    <input type="text" name="imgCode" value={this.state.imgCode} onChange={this.imgCodeHandlerChange}/> <img src={this.state.imgBase64} alt="validation code"/>
                    <button className="btn btn-warning" onClick={this.sendCodeClicked}>发送验证码</button>
                    <button className="btn btn-success" onClick={this.loginClicked}>登录</button>
                </div>
            </div>
        )  
    }
    
}

export default LoginComponent;