/**
 * Created by dell on 2015-12-15.
 */

'use strict';
var React = require('react-native');
var Login = require('./Login_Module/Login');

var {
    Image,
    Text,
    View,
    StyleSheet,
    TouchableHighlight
    } = React;

var styles= StyleSheet.create({

    //总框架
    container :{
        //borderColor:"#9370DB",//紫色
        //borderWidth:3,

        flexDirection: 'column',//垂直分
        width:1024,
        height:743,
        alignItems: 'center'//水平居中
    },

    //界面
    content : {
        //borderColor:"#FFC0CB",//粉红色
        //borderWidth:3,

        flexDirection: 'column',//垂直分
        flex :19,//占container的19份

        //alignItems: 'center',//水平居中
    },

    //页脚
    footer :{
        borderColor:'#c0c0c0',//灰色色
        borderWidth:1,
        flex:1,//占container的1份

        width: 1024,
        alignItems: 'center',//水平居中
        justifyContent: 'center'//垂直居中
    },

    //界面内上侧
    content_top :{
        //borderColor:'#FFFF00',//黄色
        //borderWidth:3,
        flex:1,//占content的1份

        alignItems: 'center',//水平居中
        marginTop: 50,
        //justifyContent: 'flex-end'//垂直居中
    },

    //对话文字框架
    content_text :{
        //borderColor:'#FFFF00',//黄色
        //borderWidth:3,

        width:500,
        marginTop: 50,
        marginLeft: 130
    },

    //对话文字字体
    content_text_type :{
        fontSize: 40
    },

    //界面内下侧
    content_bottom : {
        //borderColor:'#006400',//深绿色
        //borderWidth:3,
        flex:1,//占content的1份

        flexDirection: 'row',//垂直分
        marginBottom: 50,//下侧预留50
        alignItems: 'center'//水平居中
    },

    //界面内下左侧
    content_bottom_left : {
        //borderColor:"#9370DB",//紫色
        //borderWidth:3,
        flex:1,//占1份

        marginLeft: 150,//左侧预留50
        alignItems: 'center'//水平居中
    },

    //界面内下右侧
    content_bottom_right : {
        //borderColor:"#FFC0CB",//粉红色
        //borderWidth:3,
        flex:3,//占1份

        marginTop: 200,//下侧预留50
        marginLeft: 50,//左侧预留50
    },

    //页脚文字格式
    text: {
        fontSize: 15
    },

    //Let's go 按钮
    button: {
        height: 51,
        width: 290,
        borderRadius: 8,
        //justifyContent: 'center'
    }
});

//class Login extends  Component {
var Welcome = React.createClass({

    getInitialState(){
        return {
            username: null,
            password: null,
            secure: true,
            IsLogin: false,
            Message: "NO"
        };
    },

    onLetsgoPressed(){
        const {navigator } = this.props;
        if (navigator) {
            navigator.push({
                name: 'Login',
                component: Login
            })
        }
    },


    /* 渲染函数 ，通过return 返回一个布局 */
    render(){
        return (
            <View style={styles.container}>


                <View style={styles.content}>
                    <Image style={{height: 706}} resizeMode='cover' source={require('../image/welcome/welcome_background.png') }>
                        <View style={styles.content}>
                            {/*图片上放*/}
                            {/*上侧：对话框*/}
                            <View style={styles.content_top}>
                                <Image style={{width: 700, height: 300}} resizeMode='stretch' source={require('../image/welcome/dialogue_box.png') }>
                                    <View style={styles.content_text}>
                                        <Text style={styles.content_text_type}>Hi! I'm Peale. </Text>
                                        <Text style={styles.content_text_type}>Do you want to play with me?</Text>
                                    </View>
                                </Image>
                            </View>

                            {/*下侧*/}
                            {/*Peale*/}
                            <View style={styles.content_bottom}>
                                <View style={styles.content_bottom_left}>
                                    <Image source={require('../image/welcome/peal.png') }>
                                    </Image>
                                </View>

                                {/*Let's go 按钮*/}
                                <View style={styles.content_bottom_right}>
                                    <TouchableHighlight
                                        style={styles.button}
                                        underlayColor='white'
                                        onPress={this.onLetsgoPressed}>
                                        <Image source={require('../image/welcome/lets_go_button.png') }/>
                                    </TouchableHighlight>
                                </View>
                            </View>
                        </View>
                    </Image>
                </View>

                {/*页脚*/}
                <View style={[styles.footer,{height: 40}]}>
                    <Text style={[styles.text,{ color: 'grey'}]}>首都师范大学</Text>
                </View>
            </View>
        );
    }
})

module .exports=Welcome;