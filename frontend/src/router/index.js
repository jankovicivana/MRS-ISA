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
import AdvertiserProfile from "../components/AdvertiserProfile";
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
import AdminViewInstructor from "../components/AdminViewInstructor";
import AdminViewBoatOwners from "../components/AdminViewBoatOwners";
import UpcomingReservations from "../components/UpcomingReservations";
import CurrentReservations from "../components/CurrentReservations";
import Report from "../components/Report";
import ClientSubscriptions from "../components/ClientSubscriptions";
import LoginPage from "../components/LoginPage";
import HomepageCottageOwner from "../components/HomepageCottageOwner";
import HomepageBoatOwner from "../components/HomepageBoatOwner";
import FishingInstructorHomepage from "../components/FishingInstructorHomepage";
import Revision from "../components/Revision";

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
      path: '/ReservationHistory',
      name:'ReservationHistory',
      component: ReservationHistory
    },{
      path: '/fishingInstructor/Availability',
      name:'InstructorAvailability',
      component: InstructorAvailability
    },
    {
      path:'/AdvertiserProfile',
      name:'AdvertiserProfile',
      component: AdvertiserProfile
    },
    {
      path:'/boats/BoatProfile',
      name:'BoatProfile',
      component: BoatProfile
    },{
      path: '/admin/AddAdministrator',
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
      path: '/admin/viewInstructors',
      name:'AdminViewInstructor',
      component: AdminViewInstructor

    },{
      path: '/admin/viewBoatOwners',
      name:'AdminViewBoatOwners',
      component: AdminViewBoatOwners

    },{
      path: '/admin/revisions',
      name:'Revison',
      component: Revision

    },
    {
      path: '/client/clientHomepage',
      name: 'ClientHomepage',
      component: ClientHomepage
    },
    {
      path: '/fishingInstructor/instructorHomepage',
      name: 'FishingInstructorHomepage',
      component: FishingInstructorHomepage
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
      path: '/upcomingReservations',
      name: "UpcomingReservations",
      component: UpcomingReservations
    },
    {
      path: '/currentReservations',
      name: "CurrentReservations",
      component: CurrentReservations
    },
    {
      path: '/createReport/:id',
      name: "CreateReport",
      component: Report
    },
    {
      path: '/client/subscriptions',
      name: "ClientSubscriptions",
      component: ClientSubscriptions
    },
    {
      path: '/login',
      name: "LoginPage",
      component: LoginPage
    },
    {
      path: '/cottageOwner/cottageOwnerHomepage',
      name: 'HomepageCottageOwner',
      component: HomepageCottageOwner
    },
    {
      path: '/boatOwner/boatOwnerHomepage',
      name: 'HomepageBoatOwner',
      component: HomepageBoatOwner
    }
  ]
})
