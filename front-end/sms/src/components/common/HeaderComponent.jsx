import React, {Component} from 'react'
import AuthenticationService from '../auth/AuthenticationService.js'
import {Link} from 'react-router-dom'
import {withRouter} from 'react-router'

class HeaderComponent extends Component{
    render(){
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn()
        


        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div><a href="https://github.com/wed91007/springboot-sms-auth" className="navbar-brand">短信验证程序</a></div>
                    {/* <ul className="navbar-nav">
                        
                        {isUserLoggedIn && <li><Link  className="nav-link" to="/welcome/:phone">Home</Link></li>}
                        {isUserLoggedIn && <li><Link  className="nav-link" to="/todos">Todos</Link></li>}
                    </ul> */}
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                        {isUserLoggedIn && <li><Link  className="nav-link" to="/login">首页</Link></li>}
                        {/* {isUserLoggedIn && <li><Link  className="nav-link" to="/logout" onClick={AuthenticationService.logout}>退出</Link></li>} */}
                    </ul>
                </nav>
            </header>
            
        )
    }
}


export default withRouter(HeaderComponent);