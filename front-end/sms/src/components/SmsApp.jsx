import React, {Component} from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
// import AuthenticatedRoute from './AuthenticatedRoute.jsx'
import LoginComponent from './LoginComponent.jsx'
import ErrorComponent from './ErrorComponent.jsx'


class SmsApp extends Component{
    render() {
        return(
            <div className="SmsApp">
                <Router>
                    <>
                        {/* <HeaderComponent></HeaderComponent> */}
                        <Switch>
                        <Route path="/" exact component={LoginComponent}/>
                        <Route path="/login" component={LoginComponent}/>
                        {/* <AuthenticatedRoute path="/welcome/:name" component={WelcomeComponent}/> */}
                        <Route component={ErrorComponent}/>
                        </Switch>
                        {/* <FooterComponent></FooterComponent> */}
                    </>
                </Router>
                {/* <LoginComponent></LoginComponent>
                <WelcomeComponent></WelcomeComponent> */}
            </div>
        )
    }
}

export default SmsApp;