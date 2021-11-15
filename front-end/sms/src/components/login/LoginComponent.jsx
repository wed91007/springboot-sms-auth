import React, {Component} from 'react'
import AuthenticationService from '../auth/AuthenticationService.js'
import './../UI/css/main.css'
import './../UI/css/util.css'
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
        AuthenticationService.executeImageCodeGet()
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
                
                if(response.data.status === "OK"){
                    this.setState({smsSendSuccess: true})
                }
                
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
        this.refreshImageCode();
        
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
            <div class="limiter">
                <div class="container-login100">
                    <div class="wrap-login100 p-t-50 p-b-90">
                            <span class="login100-form-title p-b-51">
                                用户登录
                            </span>
                        <div>
                            {this.state.repeatedClick && <div className="alert alert-warning">请求过于频繁,请稍再试</div>} 
                            {this.state.smsSendSuccess && <div className="alert alert-warning">验证码已发送</div>}
                            {this.state.smsSendSuccessError && <div className="alert alert-warning">验证码发送失败</div>}
                            {this.state.hasLogFailed && <div className="alert alert-warning">登陆失败</div>}
                            {this.state.showSuccessMessage && <div>登录成功</div>}
                        </div>
                
                        <div class="wrap-input100 validate-input m-b-16">
                            <input class="input100" type="text" name="phone" value={this.state.phone} onChange={this.phoneHandlerChange} placeholder="手机号"/>
                            <span class="focus-input100"></span>
                        </div>
                
                    <div class="wrap-input100 validate-input m-b-16">
                        <input class="input100" type="text" name="imgCode" value={this.state.imgCode} onChange={this.imgCodeHandlerChange} placeholder="图片验证码"/>
                        <span class="focus-input100"></span>
                    </div>

                    <div>
                        <img src={this.state.imgBase64} alt="validation code"/>
                    </div>
                    
                    <div class="container-login100-form-btn m-t-17">
                        <button class="login100-form-btn" onClick={this.sendCodeClicked}>
                            发送验证码
                        </button>
                    </div>

                    <div class="wrap-input100 validate-input m-t-17">
                        <input class="input100" type="text" name="sms" value={this.state.sms} onChange={this.smsHandlerChange} placeholder="短信验证码"/>
                        <span class="focus-input100"></span>
                    </div>
                    
                    <div class="container-login100-form-btn m-t-17">
                        <button class="login100-form-btn" onClick={this.loginClicked}>
                            登录
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
        )  
    }
    
}

export default LoginComponent;