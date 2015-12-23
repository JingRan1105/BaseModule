/**
 * Created by dell on 2015/11/25.
 */
'use strict';
var React = require('react-native');
var ForgetPassWord=require('./ForgetPassWord');
var Register = require('./Register');
var PreTestWelcome = require('./../PreTest_Module/PreTestWelcome');

var user = [];

var {
    Image,
    TextInput,
    Text,
    View,
    StyleSheet,
    TouchableHighlight,

    TouchableOpacity
    } = React;

var styles= StyleSheet.create({
    container :{
        borderColor:"#c0c0c0",
        borderWidth:1,
        flexDirection: 'column',
        height:743
    },
    _content : {
        borderColor:"#c0c0c0",
        borderWidth:1,
        flexDirection: 'row',
        flex :19,

    },

    //页脚
    footer :{
        borderColor:'#c0c0c0',//灰色
        borderWidth:1,
        flex:1,//占1份
        width: 1024,
        //height: 40,

        alignItems: 'center',//水平居中
        justifyContent: 'center'//垂直居中
    },

    _content_left :{
        flex:1,
        alignSelf:'flex-end',
        marginLeft:50,

    },

    photo_image: {
        marginBottom:20,
    },

    _content_right : {
        marginLeft:9,
        flex:3,
        marginTop: 200,
    },

    login_1 :{
        flexDirection: 'row',
        width:400,
    },
    text: {
        fontSize: 15,
    },
    button: {
        height: 40,
        width:400,
        //flex: 1,
        //flexDirection: 'row',
        borderColor: '#48BBEC',
        borderWidth: 1,
        borderRadius: 8,
        marginTop: 10,
        marginBottom: 10,
        justifyContent: 'center'
    },

    searchInput: {
        height: 60,
        width : 380,
        padding: 4,
        marginRight: 5,
        flex: 2,
        fontSize: 18,
        borderWidth: 1,
        borderColor: '#48BBEC',
        borderRadius: 8,
        color: '#48BBEC'
    },
});


//class Login extends  Component {
var Login = React.createClass({

    getInitialState(){
        return {

            username: null,
            password: null,
            secure: true,
            IsLogin: false,
        };
    },

    onPressForgetPassWord() {
        const {navigator} = this.props;
        if (navigator) {
            navigator.push({
                name: 'ForgetPassWord',
                component: ForgetPassWord,
            })
        }
    },

    onPressRegister() {
        const {navigator} = this.props;
        if (navigator) {
            navigator.push({
                name: 'Register',
                component: Register,
            })
        }
    },

    //跳转到下一页
    PreTestWelcome() {
        const {navigator} = this.props;
        if (navigator) {
            navigator.push({
                name: 'PreTestWelcome',
                component: PreTestWelcome,

                //这里多出了一个 params 其实来自于<Navigator 里的一个方法的参数...
                params: {
                    paramsUser: user
                }
            })
        }
    },

    _handleResponse(responseText) {
        var jonstr = JSON.parse(responseText);

        var status = jonstr.status;
        for(var i=0; i<jonstr.result.data.length; i++){
            user.push({
                username:jonstr.result.data[i].userName,
            })
            this.setState({
                Message: 'username: '+user[i].username,
            })
        }

        if(status =='1') {
            this.PreTestWelcome();
        }
    },


    onLoginPressed() {

        var hh = 'http://192.168.0.117:8088/iqasweb/mobile/user/login.html?' + 'password=' + this.state.password + '&' + 'userName=' + this.state.username;

        fetch(hh)
            .then((response) => response.text())
            .then(responseText => {
                this._handleResponse(responseText);
            })
            .catch((error) => {
                this.setState({
                    Message: "捕获到错误",
                })
            });
    },

    /* 渲染函数 ，通过return 返回一个布局 */
    render(){
        var IsLogin = this.state.IsLogin;
        if (IsLogin) {
            this.PreTestWelcome();
        }
        else {
            return (
                <View style={styles.container}>
                    <View style={styles._content}>

                        <View style={styles._content_left}>
                            <Image source={require('../../image/login/peal.png') }></Image>
                        </View>

                        <View style={styles._content_right}>

                            <View style={[styles.login_1,{ marginTop:25}]}>
                                <Image source={require('../../image/login/username.png')} style={styles.photo_image}/>
                                <TextInput
                                    style={styles.searchInput}
                                    onChangeText={(text)=>this.setState({username:text})}
                                    value={this.state.username}
                                    placeholder="your login name"
                                />
                            </View>

                            <View style={[styles.login_1,{marginTop:25 }]}>
                                <Image source={require('../../image/login/password.png')} style={styles.photo_image}/>
                                <TextInput
                                    style={styles.searchInput}
                                    onChangeText={(text) =>this.setState({password:text})}
                                    value={this.state.password}
                                    placeholder="password"
                                    secureTextEntry={this.state.secure}
                                />
                            </View>

                            <View style={[styles.login_1,{height: 50}]}>
                                <TouchableOpacity  >
                                    <Text style={{marginTop:10}}
                                          onPress={() =>this.onPressForgetPassWord()}>忘记密码?</Text>
                                </TouchableOpacity>
                                <TouchableOpacity >
                                    <Text style={{marginTop:10,marginLeft:300}}
                                          onPress={() =>this.onPressRegister()}>注册</Text>
                                </TouchableOpacity>
                            </View>


                            <TouchableHighlight
                                style={styles.button}
                                underlayColor='#99d9f4'
                                onPress={this.onLoginPressed}>
                                <Image source={require('../../image/login/login_button.png')} style={{width: 400}}/>
                            </TouchableHighlight>

                        </View>
                    </View>
                    <View style={styles.footer}>
                        <Text> {this.state.Message}  </Text>
                        <Text style={[styles.text,{ color: 'grey'}]}>首都师范大学</Text>
                    </View>

                </View>
            );
        }
    }
});



module .exports=Login;