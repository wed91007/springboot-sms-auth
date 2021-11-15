import React, {Component} from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import AuthenticatedRoute from './auth/AuthenticatedRoute.jsx'
import LoginComponent from './login/LoginComponent.jsx'
import ErrorComponent from './common/ErrorComponent.jsx'
import WelcomeComponent from './welcome/WelcomeComponent.jsx'
import HeaderComponent from './common/HeaderComponent.jsx'
import FooterComponent from './common/FooterComponent.jsx'
import LogoutComponent from './common/LogoutComponent.jsx'

class SmsApp extends Component{
    render() {
        return(
            <div className="SmsApp">
                <Router>
                    <>
                        <HeaderComponent></HeaderComponent>
                        <Switch>
                        <Route path="/" exact component={LoginComponent}/>
                        <Route path="/login" component={LoginComponent}/>
                        <AuthenticatedRoute path="/welcome/:phone" component={WelcomeComponent}/>
                        <AuthenticatedRoute path="/logout" component={LogoutComponent}/>
                        <Route component={ErrorComponent}/>
                        </Switch>
                        <FooterComponent></FooterComponent>
                    </>
                </Router>
                {/* <LoginComponent></LoginComponent>
                <WelcomeComponent></WelcomeComponent> */}
            </div>
        )
    }
}

export default SmsApp;