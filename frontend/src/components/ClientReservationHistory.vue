<template>
  <section class="profile_main vh-100">
    <client-navbar></client-navbar>
    <div class="content is-medium">
      <div class="mask flex align-items-center pt-5 h-100">
        <div class="container h-100">
          <div class="row flex justify-content-center h-100">
            <div class="col-11">
              <div class="card">
                <div class="card-body p-5">
                  <h1 class="title">Reservation history</h1>
                  <select ref="type" v-model="type">
                    <option>Cottage</option>
                    <option>Boat</option>
                    <option>Adventure</option>
                  </select>
                  <hr/>
                  <div>
                    <table>
                      <thead>
                      <tr>
                        <th class="sortable" v-on:click="orderReservations('name')">Name</th>
                        <th class="sortable" v-on:click="orderReservations('startDateTime')">Start</th>
                        <th class="sortable" v-on:click="orderReservations('endDateTime')">End</th>
                        <th>Location</th>
                        <th class="sortable" v-on:click="orderReservations('price')">Price</th>
                        <th>Review</th>
                      </tr>
                      </thead>

                      <tbody>
                      <tr v-for="r in orderedReservations" v-if="type === r.entity.type">
                        <td>{{r.entity.name}}</td>
                        <td>{{r.startDateTime[2]+"."+r.startDateTime[1]+"."+r.startDateTime[0]+"."}}</td>
                        <td>{{r.endDateTime[2]+"."+r.endDateTime[1]+"."+r.endDateTime[0]+"."}}</td>
                        <td>{{r.entity.address.city+", "+r.entity.address.country}}</td>
                        <td>{{r.price}}</td>

                        <td><button v-on:click="review(r)">Write a review</button></td>
                      </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import ClientNavbar from "./header/ClientNavbar";
import axios from "axios";
import _orderBy from 'lodash/orderBy';
export default {
  name: "ClientReservationHistory",
  components: {ClientNavbar},
  data: function (){
    return {
      reservations: '',
      type: '',
      currentSort: 'name',
      currentSortDir: 'asc'
    }
  },

  computed: {
    orderedReservations: function (param){
      return _orderBy(this.reservations, this.currentSort, this.currentSortDir);
    }
  },

  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/reservation/findHistoryByClient", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (
        this.reservations = response.data
      ));
    this.type = "Cottage";
  },

  methods: {
    review: function (res){
      alert('not implemented yet')
    },

    orderReservations: function (param){
      if(param === this.currentSort){
        this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
      }
      this.currentSort = param;
      this.orderedReservations = _orderBy(this.reservations, param);
    }

  }

}
</script>

<style scoped>

.content{
  height: 80%;
}

.card{
  border-radius: 15px;
  background: #ecd9c6;
  height: 100%;
}

tr{
  background: #ecd9c6;
}

button{
  background: #2e6b6b;
  border-radius: 8px;
  color: #FFFFFF;
  border-color: #FFFFFF
}

select{
  border: none;
  background-color: #ecd9c6;
}

th.sortable:hover{
  cursor: pointer;
}
</style>
