/**
 * Created by dell on 2015/12/1.
 */
'use strict';
var React = require('react-native');
var {
    View,
    Text,
    StyleSheet,
    Image,
    TextInput,

    } = React;

var styles= StyleSheet.create({
    container :{
        borderColor:"red",
        borderWidth:1,
        flexDirection: 'column',
        width:1024,
        height:743,
        //margin:10
    },

    content : {
        flex: 19,
        borderColor:'red',
        borderWidth:1,
        flexDirection:'row'
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

    //页脚文字格式
    text: {
        fontSize: 15
    },

    content_left :{
        borderColor:'black',
        borderWidth:1,

        alignSelf:'flex-end',

    },

    content_center : {
        borderColor:'red',
        borderWidth: 1,
        marginTop:20,
        width:800,
    },

    content_center_View :{
        borderWidth: 1,
        borderColor:'blue',
        margin:100
    },

    username: {
       fontSize:20,
        fontWeight:'bold',
    },

    yourTestProblem :{

    },

});

var ForgetPassWord = React.createClass ({

    getInitialState() {
        return {
            userInformation: false,
        };
    },

    componentDidMount: function() {
        //这里获取从Login传递过来的参数: user
        this.setState({
            user: this.props.paramsUser
        });
    },

    Information_Testify() {
         return (
             <View style ={styles.content_center_View} >
                 <Text style={styles.username}>用户名：</Text>
                 <TextInput></TextInput>
                 <Text style={styles.content_center_View}>选择密保问题</Text>

             </View>
         );
    },

    Reset_PassWord() {

    },
    render() {
        var userInformation= this.state.userInformation ?
            ( this.Reset_PassWord()):
            (this.Information_Testify());

        return (
            <View style={styles.container}>
               <View style={styles.content}>
                   <View style={styles.content_left}>
                       <Image
                           source ={require('../../image/login/peal.png')}
                       />
                   </View>
                   <View style={styles.content_center}>
                       <Image
                           source={require('../../image/login/forget_main.png')}
                          >
                           {userInformation}
                       </Image>
                   </View>
               </View>

                {/*页脚*/}
                <View style={styles.footer}>
                    <Text style={[styles.text,{ color: 'grey'}]}>首都师范大学</Text>
                </View>

            </View>
        );
    }
});

module .exports = ForgetPassWord;