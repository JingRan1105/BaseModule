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
    SwitchAndroid,
    TouchableHighlight,
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

    nav :{
        borderColor: "red",
        borderWidth:1,
        height:130,
        flexDirection: 'row',

    },

    content :{
        flex: 19,
        borderColor: "red",
        borderWidth:1,
        alignSelf:'center',
        //marginRight:50,
        flexDirection: 'row',
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
    footerText: {
        fontSize: 15
    },

    content_L :{
        borderColor: "red",
        borderWidth:1,
        width:380,
        height:380,
        marginLeft:20,
        marginTop:50,

    },

    content_M: {
        borderColor: "yellow",
        borderWidth:1,
        height:380,
        marginTop:20,
        backgroundColor:'yellow',

    },

    content_R: {
        borderColor: "red",
        borderWidth:1,
        width:400,
        height:380,
        marginTop:50,
        marginRight:20,

    },

    switch :{
         width:50,
        marginLeft:5,

    },

    text_input : {
            width:250,
        marginLeft:60,
    },

    text:{
        color :"66ccff",
        fontSize: 18,
        fontFamily:"Times New Roman",

    },

    button: {
        height:40,
        width:300,
        flexDirection: 'row',
        backgroundColor: '#48BBEC',
        borderColor: '#48BBEC',
        borderWidth: 1,
        borderRadius: 18,
        marginRight:50,
        alignSelf: 'flex-end',
       justifyContent: 'center',
       // alignItems:'center'
    },

    buttonText: {
        fontSize: 18,
        color: 'white',
        alignSelf: 'center'
    },
});



var Register = React.createClass ({

    componentDidMount: function() {
        //这里获取从Login传递过来的参数: user
        this.setState({
            user: this.props.paramsUser
        });
    },

    getInitialState() {
        return {
            userInformation: false,
            secure:true,
            SwitchIsBoy: true,
            SwitchIsGirl: false,
            username:null,
            password:null,
            message:'234243l',
        };
    },

   SelectGirl() {
       if(this.state.SwitchIsGirl){
           this.setState({
               SwitchIsGirl:false,
               SwitchIsBoy: true,
           });
       }
        else{
           this.setState({
               SwitchIsGirl:true,
               SwitchIsBoy: false,
                }
           )}
   },

  SelectBoy() {
       if(this.state.SwitchIsBoy){
           this.setState({
               SwitchIsBoy:false,
               SwitchIsGirl:true,
               })
       }else{
           this.setState({
               SwitchIsBoy:true,
               SwitchIsGirl:false,
           })
       }
   },

    register_info1(){
       return (
           <View  style={styles.content_L}>
               <Text style={styles.text}>用户名：</Text>
               <TextInput onChangeText={(text)=>this.setState({username:text})}
                            style={styles.text_input}
                            //maxLength='5'
                            underlineColorAndroid="#48BBEC"
                            value={this.state.username}/>
               <Text style={styles.text}>密码：</Text>
               <TextInput onChangeText={(text) =>this.setState({password:text})}
                           value={this.state.password}
                           style={styles.text_input}
                          underlineColorAndroid="#48BBEC"
                           secureTextEntry={this.state.secure}/>
               <Text  style={styles.text}>性别：</Text>
               <View style={{flexDirection:'row', marginLeft:10, marginTop:20}}>
                   <Text>女</Text>
                   <SwitchAndroid
                       style={styles.switch}
                       onValueChange={this.SelectGirl}
                       onTintColor="blue"
                     //  onPress={this.SelectGirl()}
                       value={this.state.SwitchIsGirl} />
               </View>
               <View style={{flexDirection:'row', marginLeft:10,marginTop:20}}>
                   <Text>男</Text>
                   <SwitchAndroid
                       style={styles.switch}
                       onValueChange={this.SelectBoy}
                       value={this.state.SwitchIsBoy} />
               </View>

           </View>
       );
    },

    register_info2(){

    },

    urlForQueryAndRegister( ){
        var data = {
            userName:this.state.username,
            password :this.state.password,
            sex:this.state.SwitchIsBoy,
        }
        var querystring = Object.keys(data)
            .map(key => key + '='+ encodeURIComponent(data[key]))
            .join('&');
        this.setState({
            message:querystring,
        })
        return 'http://192.168.0.109:8088/iqasweb/mobile/user/register.html?'+
        querystring;

    },

    _executeQuery(query) {
      fetch(query);
    },

    Deal_register_information() {
            var query= this.urlForQueryAndRegister();
            this._executeQuery(query);

    },

    render() {
       var  register_info1= this.register_info1();
       var  register_info2= this.register_info2();
        return (
            <View style={styles.container}>
                <View style={styles.nav}>
                    <Text>返回</Text>
                    <View style={{marginLeft:1200, borderColor:'red', borderWidth:1,}}>
                    <Image source={require('../../image/login/logo_register.png')}/>
                    </View>
                </View>
                <View style={styles.content}>
                    <Image source={require('../../image/login/Forget_back.png')}
                            style={{width:800,height:500}}>
                            <View style={{flexDirection:'row'}}>
                            {register_info1}
                                <View style={styles.content_M}></View>
                                <View style={styles.content_R}>{register_info2}</View>
                            </View>
                        <TouchableHighlight style={styles.button} underlayColor='#99d9f4'
                                              onPress={this.Deal_register_information}>
                            <Text style={styles.buttonText}>注册</Text>
                        </TouchableHighlight>
                    </Image>
                </View>

                <Text> {this.state.message}  </Text>

                {/*页脚*/}
                <View style={styles.footer}>
                    <Text style={[styles.footerText,{ color: 'grey'}]}>首都师范大学</Text>
                </View>
            </View>
        );
    }
});

module .exports = Register