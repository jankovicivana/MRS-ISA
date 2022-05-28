import Vue from 'vue'
import VueCarousel from 'vue-carousel';
import Router from 'vue-router'
import VueNotification from "vue-notification";
import {LMap,LTileLayer,LMarker} from "vue2-leaflet";
import {Icon} from "leaflet";
import 'leaflet/dist/leaflet.css'
import StarRating from "vue-star-rating";
import VueLodash from "vue-lodash";
import lodash from 'lodash';
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
import UpdateBoat from "../components/UpdateBoat";
import ClientHomepage from "../components/ClientHomepage";
import MainHomepage from "../components/MainHomepage";
import BrowseCottages from "../components/BrowseCottages";
import BrowseBoats from "../components/BrowseBoats";
import BrowseAdventures from "../components/BrowseAdventures";

import ReservationHistory from "../components/ReservationHistory";
import InstructorAvailability from "../components/InstructorAvailability";
import UpcomingReservations from "../components/UpcomingReservations";
import CurrentReservations from "../components/CurrentReservations";
import Report from "../components/Report";
import ClientSubscriptions from "../components/ClientSubscriptions";
import Reservations from "../components/Reservations";
import LoginPage from "../components/LoginPage";
import HomepageCottageOwner from "../components/HomepageCottageOwner";
import HomepageBoatOwner from "../components/HomepageBoatOwner";
import FishingInstructorHomepage from "../components/FishingInstructorHomepage";
import Revision from "../components/Revision";
import AdminViewUsers from "../components/AdminViewUsers";
import Registration from "../components/Registration";
import ClientReservationHistory from "../components/ClientReservationHistory";
import ClientUpcomingReservations from "../components/ClientUpcomingReservations";

Vue.use(Router)
Vue.use(VueCarousel)
Vue.use(VueNotification)
Vue.use(StarRating)
Vue.use(VueLodash, {name: 'custom', lodash: lodash})
Vue.component('l-map',LMap)
Vue.component('l-tile-layer',LTileLayer)
Vue.component('l-marker',LMarker)

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

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
      path: '/cottages/CottageProfile/:id',
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
      path: '/admin/viewUsers/:userType',
      name:'AdminViewClients',
      component: AdminViewUsers

    }
    ,{
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
    },
    {
      path: '/client/reservations',
      name: "Reservations",
      component: Reservations
    },
    {
      path: '/register',
      name: "Registration",
      component: Registration
    },
    {
      path: '/client/reservationHistory',
      name: "ClientReservationHistory",
      component: ClientReservationHistory
    },
    {
      path: '/client/upcomingReservations',
      name: "ClientUpcomingReservations",
      component: ClientUpcomingReservations
    }
  ]
})
