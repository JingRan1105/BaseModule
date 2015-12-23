/**
 * Created by dell on 2015-12-23.
 */

'use strict';
var React = require('react-native');
var MyBag = require('./MyBag');

var {
    Image,
    Text,
    View,
    StyleSheet,
    TouchableOpacity
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

    //页脚文字格式
    text: {
        fontSize: 15
    },

});

//class Login extends  Component {
var FreeBarrierResult = React.createClass({

    getInitialState(){
        return {
            user: []
        };
    },

    componentDidMount: function() {
        //这里获取从Login传递过来的参数: user
        this.setState({
            user: this.props.paramsUser
        });
    },

    //跳转到下一页
    pressButtonSubmit() {
        const {navigator} = this.props;
        if (navigator) {
            navigator.push({
                name: 'MyBag',
                component: MyBag,

                //这里多出了一个 params 其实来自于<Navigator 里的一个方法的参数...
                params: {
                    paramsUser: this.state.user
                }
            })
        }
    },


    /* 渲染函数 ，通过return 返回一个布局 */
    render(){
        return (
            <View style={styles.container}>

                <View style={styles.content}>
                    <Text style={styles.text}>免费获取结果</Text>

                    <TouchableOpacity>
                        <Text onPress={() =>this.pressButtonSubmit()}>
                            返回我的背包
                        </Text>
                    </TouchableOpacity>

                </View>

                {/*页脚*/}
                <View style={styles.footer}>
                    <Text style={[styles.text,{ color: 'grey'}]}>首都师范大学</Text>
                    <Text>用户信息:{JSON.stringify(this.state.user)}</Text>
                </View>
            </View>
        );
    }
})

module .exports=FreeBarrierResult;