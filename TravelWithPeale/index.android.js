/**
 * Created by dell on 2015-12-22.
 */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
var React = require('react-native');
//var Home = require('./AllForeground/Home');
var Home = require('./AllForeground/Login_Module/Login');

var{
    AppRegistry,
    Navigator,
    } = React;

var HelloWorld = React.createClass ({
    render : function () {
        var defaultName = 'Login';
        var defaultComponent = Home;

        return (
            <Navigator
                initialRoute={{name: defaultName, component: defaultComponent}}
                configureScene={ () => {
                return Navigator.SceneConfigs.VerticalDownSwipeJump;
                }}
                renderScene={(route, navigator) => {
                let Component = route.component;
                if(route.component) {
                      return<Component {...route.params } navigator={navigator } />
                }
                }}/>
        );
    }
});
AppRegistry.registerComponent('TravelWithPeale', () =>HelloWorld);
