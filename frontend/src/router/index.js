import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import AdventureProfile from "../components/AdventureProfile";
import AddAdventure from "../components/AddAdventure";
import CottageProfile from "../components/CottageProfile";
import AddCottage from "../components/AddCottage";
import ClientProfile from "../components/ClientProfile";
import UpdateCottage from "../components/UpdateCottage";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },{
      path: '/adventures/adventureProfile',
      name:'AdventureProfile',
      component: AdventureProfile
    },{
      path: '/adventures/addAdventure',
      name:'AddAdventure',
      component: AddAdventure
    },{
      path: '/cottages/CottageProfile',
      name:'CottageProfile',
      component: CottageProfile
    },{
      path: '/cottages/AddCottage',
      name:'AddCottage',
      component: AddCottage
    },{
      path: '/client/ClientProfile',
      name:'ClientProfile',
      component: ClientProfile
    },{
      path: '/cottages/UpdateCottage',
      name:'UpdateCottage',
      component: UpdateCottage
    }
  ]
})
