/**
 * Created by dell on 2015-12-15.
 */

'use strict';
var React = require('react-native');
var PreTestQuestion = require('./PreTestQuestion');
var BarrierMap = require('./../BarrierMap_Module/BarrierMap');

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
        marginTop: 30,
        marginLeft: 130
    },

    //对话文字字体
    content_text_type :{
        fontSize: 30
    },

    //对话文字框架
    content_botton :{
        //borderColor:'#FFFF00',//黄色
        //borderWidth:3,

        justifyContent: 'space-around',//每个项目两侧的间隔相等。所以，项目之间的间隔比项目与边框的间隔大一倍。
        flexDirection: 'row',//水平分
        marginTop: 30,
        width:500,

    },

    //界面内下侧
    content_bottom : {
        //borderColor:'#006400',//深绿色
        //borderWidth:3,
        flex:1,//占content的1份

        marginBottom: 50,//下侧预留50

    },

    //界面内下左侧
    content_bottom_left : {
        //borderColor:"#9370DB",//紫色
        //borderWidth:3,
        flex:1,//占1份

        marginLeft: 150,//左侧预留50

    },

    //页脚文字格式
    text: {
        fontSize: 15
    },

    //Let's go 按钮
    button: {
        height: 67,
        width: 200,
        borderRadius:  8,
        //justifyContent: 'center'
    }
});

//class Login extends  Component {
var PreTestWelcome = React.createClass({

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

    onNoPressed(){
        const {navigator} = this.props;
        if (navigator) {
            navigator.push({
                name: 'BarrierMap',
                component: BarrierMap,

                //这里多出了一个 params 其实来自于<Navigator 里的一个方法的参数...
                params: {
                    paramsUser: this.state.user
                }
            })
        }
    },

    onGoPressed(){
        const {navigator} = this.props;
        if (navigator) {
            navigator.push({
                name: 'PreTestQuestion',
                component: PreTestQuestion,

                //这里多出了一个 params 其实来自于<Navigator 里的一个方法的参数...
                params: {
                    //user: JSON.stringify(this.state.user)
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
                    <Image style={{height: 706}} resizeMode='cover' source={require('../../image/preTest/pretest_background.png') }>
                        <View style={styles.content}>
                            {/*图片上放*/}
                            {/*上侧：对话框*/}
                            <View style={styles.content_top}>
                                <Image style={{width: 700, height: 300}} resizeMode='stretch' source={require('../../image/preTest/dialogue_box.png') }>
                                    <View style={styles.content_text}>
                                        <Text style={styles.content_text_type}>Do you want to play a game first?</Text>
                                        <Text style={styles.content_text_type}>You can get more coins!</Text>

                                        <View style={styles.content_botton}>
                                            {/*按钮：No*/}
                                            <TouchableHighlight
                                                style={styles.button}
                                                underlayColor='white'
                                                onPress={() =>this.onNoPressed()}>
                                                <Image source={require('../../image/preTest/No.png') }/>
                                            </TouchableHighlight>

                                            {/*按钮：Go！*/}
                                            <TouchableHighlight
                                                style={styles.button}
                                                underlayColor='white'
                                                onPress={() =>this.onGoPressed()}>
                                                <Image source={require('../../image/preTest/Go!.png') }/>
                                            </TouchableHighlight>
                                        </View>
                                    </View>
                                </Image>
                            </View>

                            {/*下侧*/}
                            {/*Peale*/}
                            <View style={styles.content_bottom}>
                                <View style={styles.content_bottom_left}>
                                    <Image source={require('../../image/preTest/peal.png') }>
                                    </Image>
                                </View>

                            </View>
                        </View>
                    </Image>
                </View>

                {/*页脚*/}
                <View style={[styles.footer,{height: 40}]}>
                    <Text style={[styles.text,{ color: 'grey'}]}>首都师范大学</Text>
                    <Text>用户信息:{JSON.stringify(this.state.user)}</Text>
                </View>
            </View>
        );
    }
})

module .exports = PreTestWelcome;