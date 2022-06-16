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
                      <tr style="background: #e3c7aa">
                        <th class="sortable" v-on:click="orderReservations('name')">Name</th>
                        <th class="sortable" v-on:click="orderReservations('startDateTime')">Start</th>
                        <th class="sortable" v-on:click="orderReservations('endDateTime')">End</th>
                        <th>Location</th>
                        <th class="sortable" v-on:click="orderReservations('price')">Price</th>
                        <th>Review</th>
                        <th>Complaint</th>
                      </tr>
                      </thead>

                      <tbody>
                      <tr style="background:  #ede4da;" v-for="r in orderedReservations" v-if="type === r.entity.type">
                        <td>{{r.entity.name}}</td>
                        <td>{{r.startDateTime[2]+"."+r.startDateTime[1]+"."+r.startDateTime[0]+"."}}</td>
                        <td>{{r.endDateTime[2]+"."+r.endDateTime[1]+"."+r.endDateTime[0]+"."}}</td>
                        <td>{{r.entity.address.city+", "+r.entity.address.country}}</td>
                        <td>{{r.price}}</td>
                        <!-- treba dodati provjeru da li je vec napisao review -->
                        <td>
                            <button v-if="r.isReviewed"  class="disabled button" title="Already reviewed">Write a review</button>
                            <button v-else v-on:click="review(r)" class="button">Write a review</button>
                        </td>
                        <td>
                          <button v-if="r.isComplained"  class="disabled button" title="Already sent a complaint">Write a complaint</button>
                          <button v-else v-on:click="complain(r)" class="button">Write a complaint</button>
                        </td>
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
      currentSortDir: 'asc',
      clientId: this.$route.params.id
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

    orderReservations: function (param){
      if(param === this.currentSort){
        this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
      }
      this.currentSort = param;
      this.orderedReservations = _orderBy(this.reservations, param);
    },

    review: function (res){
      this.$router.push({path:"/client/review/"+res.id});
    },

    complain: function (res){
      this.$router.push({path:"/client/complaint/"+res.id});
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

.button{
  background: #2e6b6b;
  border-radius: 8px;
  color: #FFFFFF;
  border-color: #FFFFFF;
  text-decoration: none;
}

button.disabled{
  background: grey;
}

select{
  border: none;
  background-color: #ecd9c6;
}

select:hover{
  cursor: pointer;
}

select:focus{
  border: none;
  outline: none;
  background-color: #ecd9c6;
}

th.sortable:hover{
  cursor: pointer;
}
</style>
