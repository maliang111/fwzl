import Vuex from 'vuex';
import Vue from 'vue';


Vue.use(Vuex);


var store = new Vuex.Store({
    state : {
        username : '',
        realName : ''
    },
    mutations : {
        setUserName : function (state, username) {
            state.username = username;
        },
        setRealName : function (state, realName) {
            state.realName = realName;
        }
    }
});

export default store;
