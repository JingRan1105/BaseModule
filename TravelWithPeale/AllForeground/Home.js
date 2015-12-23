/**
 * Created by dell on 2015-12-15.
 */

'use strict';
var React = require('react-native');
var Welcome = require('./Welcome');

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
        flex: 19,//占19份
        //width:1024,

        alignItems: 'center',//水平居中
        justifyContent: 'center'//垂直居中
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

    //界面内上侧：标题
    content_top :{
        //borderColor:'#FFFF00',//黄色
        //borderWidth:3,
        flex:4,//占4份

        alignItems: 'center',//水平居中
        justifyContent: 'center'//垂直居中
    },

    //界面内下侧：Welcome按钮
    content_bottom : {
        //borderColor:'#006400',//深绿色
        //borderWidth:3,
        flex:1,//占1份

        //marginBottom: 100,//下侧预留200
        alignItems: 'center',//水平居中
    },

    //页脚文字格式
    text: {
        fontSize: 15
    },

    //标题
    title:{
        color: '#4BA687',
        fontSize: 80,
        fontFamily: 'Bodoni MT',
        fontWeight: 'bold'
    },

    //welcome按钮
    button: {
        height: 51,
        width: 290,
        borderRadius: 8,
        //justifyContent: 'center'
    }
});

//class Login extends  Component {
var Home = React.createClass({

    getInitialState(){
        return {
            username: null,
            password: null,
            secure: true,
            IsLogin: false,
            Message: "NO"
        };
    },

    onWelcomePressed(){
        const {navigator } = this.props;
        if (navigator) {
            navigator.push({
                name: 'Welcome',
                component: Welcome
            })
        }
    },


    /* 渲染函数 ，通过return 返回一个布局 */
    render(){
        return (
        <View style={styles.container}>

            <View style={styles.content}>
                <Image style={{height: 706}} resizeMode='cover' source={require('../image/home/home.png') }>
                    <View style={styles.content}>
                        {/*图片上放*/}
                        {/*上侧：标题*/}
                        <View style={styles.content_top}>
                            <Text style={styles.title}>Travel with Peale</Text>
                        </View>

                        {/*下侧：Welcome按钮*/}
                        <View style={styles.content_bottom}>
                            <TouchableHighlight
                                style={styles.button}
                                underlayColor='white'
                                onPress={() =>this.onWelcomePressed()}>
                                <Image source={require('../image/home/welcome_button.png')}/>
                            </TouchableHighlight>
                        </View>
                    </View>
                </Image>
            </View>

            {/*页脚*/}
            <View style={styles.footer}>
                <Text style={[styles.text,{ color: 'grey'}]}>首都师范大学</Text>
            </View>
        </View>
        );
    }
})

module .exports=Home;