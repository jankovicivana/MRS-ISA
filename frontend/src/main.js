// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'


import 'bootstrap/dist/css/bootstrap.min.css'
import '@popperjs/core';
import { library } from '@fortawesome/fontawesome-svg-core'
import "vue-notification";
import "vue-star-rating";

/* import specific icons */
import { faUserSecret,faCheckCircle,faUserFriends,faPercentage,faCircle,faStar,faFish,faDollarSign,faPen,faCamera,faHome,faBed,faMapMarker,faUser,faDoorOpen,faAnchor,faSailboat } from '@fortawesome/free-solid-svg-icons'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* add icons to the library */
library.add(faUserSecret,faCheckCircle,faUserFriends,faPercentage,faCircle,faStar,faFish,faDollarSign,faPen,faCamera,faHome,faBed,faMapMarker,faUser,faDoorOpen,faAnchor,faSailboat)

/* add font awesome icon component */
Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.component('star-rating', require("vue-star-rating").default);

Vue.config.productionTip = false


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
