import React, {Component} from 'react'


class WelcomeComponent extends Component{
    constructor(props){
        super(props)
        this.state = {

        }
        
    }
    render(){
        return (
        <>
            <h1>欢迎登陆!</h1>
            <div className="container">
                欢迎用户 {this.props.match.params.phone}. 
            </div>
        </>
        )
    }

}


export default WelcomeComponent;