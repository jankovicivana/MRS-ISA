<template>
  <div class="AdventureProfile">

    <section class="profile_main py-lg-3" style="background-image: url('https://images.unsplash.com/photo-1551606292-9649254815d6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=888&q=80');background-size: 100% 100%; ">
      <div class="row justify-content-lg-end" style="padding-right: 25px; margin-right: 65px" >
        <router-link class="col-1 rounded-pill" :to="{ name: 'UpdateAdventure',id:adventure.id }" style="background: #2e6b6b;margin: 5px" tag="button">Edit</router-link>
        <button type="button" class="col-1 rounded-pill" v-on:click="deleteAdventure()" style="background: #2e6b6b;margin: 5px">Delete</button>
      </div>
      <div class="container cottage_profile px-4 px-lg-5 my-5" >
        <div class="row align-items-center pt-5">
          <div class="col-md-6">
            <img class="main_photo " src="../assets/images/pic4.jpg" alt="Cottage main photo" width="100%"/>
            <div class="row thumbs pt-3 ">
              <span v-for="i in adventure.images" class="side_photo col-3 px-1" style="padding-top: 10px;"><img :src="require('../assets/images/'+i.path)" alt="Cottage photo1" class="img-responsive" width="130px" height="130px"></span>
            </div>
          </div>
          <div class="col-md-6"  >
            <div class="row">
              <div class="col-9 fw-bolder " style="font-size: 35px">{{adventure.name}}</div>
              <div class="col-3 ">Grade 5 <font-awesome-icon icon="fa-solid fa-star"/></div>
            </div>

            <div class="fs-5 m-3">
              <span class="text-decoration-line-through">$45.00</span>
              <span>$40.00</span>
            </div>
            <p class="lead p-1">{{adventure.description}}</p>
            <div class="row number_info  p-1">
              <p class="col-2"> <font-awesome-icon icon="fa-solid fa-user-friends"/>  {{adventure.maxPersonNum}}</p>
              <p class="col-2" ><font-awesome-icon icon="fa-solid fa-percentage"/> {{adventure.cancelFee}}<span style="font-size: xx-small">(cancel fee)</span> </p>
            </div>

            <div class="row p-1">
              <p style="font-size: 25px">Additional services</p>
              <div class="services">
                <p v-for="as in adventure.additionalServices"><font-awesome-icon  icon="fa-solid fa-check-circle"/> {{as.name}}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="row p-3">
          <div class="col-5" style="background: #f8f2ec;border-radius: 5%">
            <p style="font-size: 25px;">Rules</p>
            <div class="rules">
              <p v-for="r in adventure.rules"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.rule}}</p>
            </div>
          </div>

          <div class="col-5" style="background: #f8f2ec;border-radius: 5%;margin-left: 95px;padding-left: 25px">
            <p style="font-size: 25px;">Fishing equipment</p>
            <div class="rules">
              <p v-for="r in adventure.fishingEquipment"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.equipment}}</p>
            </div>
          </div>


        </div>
        <hr/>
        <div class="row col-12" style="alignment: center;">
          <div class="px-3" style="background: #f8f2ec; ">
            <h3>Fishing instructor</h3>
            <div class="row p-3">
              <div class="col-6  ">
                        <span class="side_photo p-lg-3 px-1"><img src="../assets/images/pera2.jpg" alt="Cottage photo4"
                                                                  width="330px"                       ></span>
              </div>

              <br/>

              <div class=" p-3 m-2 ">
                <h5>Short biography</h5>

                {{adventure.biography}}
              </div>



            </div>

          </div>
        </div>
        <br/>
        <hr/>
        <div class="row col-12" style="alignment: center;">
          <div class="px-3" style="background: #f8f2ec;">
            <p style="font-size: 25px;"><h4>Reservations:</h4></p>
            <div class="pl-6">
              Start date:
              <input type="date" name="startDate" placeholder="dd-mm-yyyy">
              End date:
              <input type="date" name="endDate" placeholder="dd-mm-yyyy">
              Person number:
              <input type="number" name="numPeople" min="1" max="10" style="width: 50px">
              <div class="res_button">
                <button type="button" class="btn ">Reserve</button>
              </div>
            </div>

          </div>

        </div>
        <hr/>
        <div class="row ">
          <div class="col-12" style="padding-left: 15px;alignment: center;background: #f8f2ec;">
            <p id="quick_heading">Quick reservations - enormous discounts!</p>
            <div class="row p-3">
              <div class="col-4 p-2 m-2 quick_res" v-for="q in adventure.quickReservations">
                <div>
                  <p class="res_date">{{q.startDateTime[2]+"."+q.startDateTime[1]+"."+q.startDateTime[0]+"."}} - {{q.endDateTime[2]+"."+q.endDateTime[1]+"."+q.endDateTime[0]+"."}}</p>
                  <div class="discount">{{q.discount}}%</div>
                </div>
                <p><font-awesome-icon icon="fa-solid fa-user-friends"/>  {{q.maxPersonNum}}</p>
                $<span class="text-decoration-line-through">{{q.price}}</span>
                $<span class="before_price">{{q.discountedPrice}}</span>
                <div class="quick_res_btn">
                  <button type="button" class="btn">RESERVE</button>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from "axios";


export default {
  name: "AdventureProfile",
  components: {AdventureSideBar},
  data(){
    return{
      adventure: '',
      server: process.env.VUE_APP_SERVER_PORT
    }
  },
  mounted:function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/2")
      .then(response => (this.adventure = response.data))

  },
  methods:{
    deleteAdventure:function (){
      let id = this.adventure.id
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/adventures/deleteAdventure/"+id)
        .then(response => {
          alert(response.data)
        }).catch(function error(error) {
        alert(error.response.data);
      });


    }
  }

}
</script>

<style scoped>
 .fa{
  font-size: 8px;
   color: #00b3b3;
   alignment: center;
}
</style>
