import Vue from 'vue'
import VueCarousel from 'vue-carousel';
import Router from 'vue-router'
import Hello from '@/components/Hello'
import AdventureProfile from "../components/AdventureProfile";
import AddAdventure from "../components/AddAdventure";
import CottageProfile from "../components/CottageProfile";
import AddCottage from "../components/AddCottage";
import ClientProfile from "../components/ClientProfile";
import UpdateCottage from "../components/UpdateCottage";
import UpdateAdventure from "../components/UpdateAdventure";
import FishingInstructorProfile from "../components/FishingInstructorProfile";
import CottageOwnerProfile from "../components/CottageOwnerProfile";
import BoatProfile from "../components/BoatProfile";
import AddAdministrator from "../components/AddAdministrator";
import AddBoat from "../components/AddBoat";
import AdminViewClients from "../components/AdminViewClients";
import UpdateBoat from "../components/UpdateBoat";
import ReservationHistory from "../components/ReservationHistory";


Vue.use(Router)
Vue.use(VueCarousel)

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
    },{
      path: '/adventures/UpdateAdventure',
      name:'UpdateAdventure',
      component: UpdateAdventure
    },{
      path: '/fishingInstructor/FishingInstructorProfile',
      name:'FishingInstructorProfile',
      component: FishingInstructorProfile
    },{
      path: '/fishingInstructor/ReservationHistory',
      name:'ReservationHistory',
      component: ReservationHistory
    },
    {
      path:'/cottageOwner/CottageOwnerProfile',
      name:'CottageOwnerProfile',
      component: CottageOwnerProfile
    },
    {
      path:'/boats/BoatProfile',
      name:'BoatProfile',
      component: BoatProfile
    },{
      path: '/administrator/AddAdministrator',
      name:'AddAdministrator',
      component: AddAdministrator
    },{
      path: '/boats/AddBoat',
      name:'AddBoat',
      component: AddBoat
    },{
      path: '/admin/viewClients',
      name:'AdminViewClients',
      component: AdminViewClients
    },{
      path: '/boats/UpdateBoat',
      name:'UpdateBoat',
      component: UpdateBoat
    }
  ]
})
