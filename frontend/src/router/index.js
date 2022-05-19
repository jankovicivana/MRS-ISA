import Vue from 'vue'
import VueCarousel from 'vue-carousel';
import Router from 'vue-router'
import VueNotification from "vue-notification";
import StarRating from "vue-star-rating";
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
import ClientHomepage from "../components/ClientHomepage";
import MainHomepage from "../components/MainHomepage";
import BrowseCottages from "../components/BrowseCottages";
import BrowseBoats from "../components/BrowseBoats";
import BrowseAdventures from "../components/BrowseAdventures";

import ReservationHistory from "../components/ReservationHistory";
import InstructorAvailability from "../components/InstructorAvailability";
import LoginPage from "../components/LoginPage";

Vue.use(Router)
Vue.use(VueCarousel)
Vue.use(VueNotification)
Vue.use(StarRating)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'MainHomepage',
      component: MainHomepage
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
    },{
      path: '/fishingInstructor/Availability',
      name:'InstructorAvailability',
      component: InstructorAvailability
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

    },
    {
      path: '/client/clientHomepage',
      name: 'ClientHomepage',
      component: ClientHomepage
    },
    {
      path: '/boats/UpdateBoat',
      name:'UpdateBoat',
      component: UpdateBoat
    },
    {
      path: '/cottages',
      name: "BrowseCottages",
      component: BrowseCottages
    },
    {
      path: '/boats',
      name: "BrowseBoats",
      component: BrowseBoats
    },
    {
      path: '/adventures',
      name: "BrowseAdventures",
      component: BrowseAdventures
    },
    {
      path: '/login',
      name: "LoginPage",
      component: LoginPage
    }
  ]
})
