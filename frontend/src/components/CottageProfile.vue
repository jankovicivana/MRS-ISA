<template>
  <section class="profile_main py-lg-3">
    <div class="row justify-content-lg-end" style="padding-right: 25px; margin-right: 65px" >
      <router-link class="col-1 rounded-pill" :to="{ name: 'UpdateCottage',id:cottage.id }" style="background: #2e6b6b;margin: 5px;color: white" tag="button">Edit</router-link>
      <button type="button" class="col-1 rounded-pill " v-on:click="deleteCottage()" style="background: #2e6b6b;margin: 5px;color: white">Delete</button>
    </div>
    <div class="container cottage_profile px-4 px-lg-5 my-5">
      <div class="row align-items-center pt-5">
        <div class="col-md-6">
          <carousel :per-page="1" :navigate-to="someLocalProperty" :navigationEnabled="true" :mouse-drag="false" :autoplay="false" :adjustable-height="true" :adjustable-height-easing="true">
            <slide>
              <img class="d-block w-100" src="../assets/images/cottage4.jpg" alt="First slide" >
            </slide>
            <slide>
              <img class="d-block w-100" src="../assets/images/cottage5.jpg" alt="Second slide" >
            </slide>

          </carousel>
          <div class="row thumbs pt-3 ">
            <span v-for="i in cottage.images" class="side_photo col-3 px-1" style="padding-top: 10px;"><img :src="require('../assets/images/'+i.path)" alt="Cottage photo1" class="img-responsive" width="130px" height="130px"></span>
          </div>
        </div>
        <div class="col-md-6" >
          <div class="row m-2">
            <div class="col-9 fw-bolder" style="font-size: 35px">{{cottage.name}}</div>
            <div class="col-3 pt-3">Ocena 5 <font-awesome-icon icon="fa-solid fa-star" /></div>
          </div>

          <div class="fs-5 m-3 row">
            <div class="col-6 p-2">
              Price: $<span>{{cottage.price}}</span>
            </div>
            <div class="col-6" style="position: relative;float: right;display: inline-block ">
              <p class="col-12 p-2" style="text-align: center;border-radius: 45%;background: #2e6b6b;color: white;font-size: 18px">Cottage owner: {{cottage.cottageOwnerName}}</p>
            </div>
          </div>


          <p class="lead p-3">{{cottage.description}}</p>
          <div class="row number_info p-3">
            <p class="col-2" > <font-awesome-icon icon="fa-solid fa-user-friends"/> {{cottage.maxNumPerson}}</p>
            <p class="col-2"><font-awesome-icon icon="fa-solid fa-home"/> {{num_rooms}}</p>
            <p class="col-2"><font-awesome-icon icon="fa-solid fa-bed"/> {{num_beds}}</p>
          </div>
          <div class="row p-3">
            <p style="font-size: 25px">Dodatne usluge</p>
            <div class="services">
              <p v-for="as in cottage.additionalServices"><font-awesome-icon class="small-icon" icon="fa-solid fa-check-circle" /> {{as.name}}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="row p-4">
        <div class="col-4" style="background: #f8f2ec;border-radius: 5%">
          <p style="font-size: 25px;">Pravila ponasanja</p>
          <div class="rules">
            <p v-for="r in cottage.rules"><font-awesome-icon class="fa" icon="fa-solid fa-circle"/> {{r.rule}}</p>
          </div>
        </div>
        <div class="col-8" style="padding-left: 15px;">
          <div class="px-3" style="background: #f8f2ec;">
            <p class="pt-3" style="font-size: 25px;">Rezervacija</p>
            <div class="pl-3 row">
              <div class="col-5">
                Pocetni datum:
                <input type="date" name="startDate" placeholder="dd-mm-yyyy">
              </div>
              <div class="col-5">
                Krajnji datum:
                <input type="date" name="endDate" placeholder="dd-mm-yyyy">
              </div>
              <div class="col-4 pt-5">
                Broj osoba:
                <input type="number" name="numPeople" min="1" max="10" style="width: 50px">
              </div>
              <div class="res_button col-2"><button type="button" class="btn ">Rezervisi</button></div>
            </div>

          </div>

        </div>
      </div>



      <div class="row ">
        <div class="col-12 mx-3" style="background: #f8f2ec;">
          <div class="row pt-3" style="padding-left: 10px">
            <h3 id="quick_heading" class="col-10">Brza rezervacija - jos malo pa nestalo!</h3>
            <span class="col-2" style="float: right;background: #f8f2ec;">
            <button type="button" v-on:click="showModal()" style="color: white;background: #c91d1d;" class="btn btn-info btn-lg ">Add new</button>
            <AddQuickReservation
              :id="cottage.id"
              style="width: 300px"
              v-show="isModalVisible"
              v-on:click="closeModal()"
            />
          </span>
          </div>

          <div class="row p-3">
            <div class="col-4 p-3 m-2 quick_res zoom" v-for="q in cottage.quickReservations">
              <div>
                <h4 class="res_date">{{q.startDateTime[2]+"."+q.startDateTime[1]+"."+q.startDateTime[0]+"."}} - {{q.endDateTime[2]+"."+q.endDateTime[1]+"."+q.endDateTime[0]+"."}}</h4>
                <div class="discount">{{q.discount}}%</div>
              </div>
              <p class="py-2"><font-awesome-icon icon="fa-solid fa-user-friends"/> {{q.maxPersonNum}}</p>
              $<span class="text-decoration-line-through">{{q.price}}</span>
              $<span class="before_price">{{q.discountedPrice}}</span>
              <div class="quick_res_btn"><button type="button" class="btn">REZERVISI</button></div>
            </div>



          </div>

        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import AddQuickReservation from "./AddQuickReservation";

export default {
  name: "CottageProfile",
  components:{
    AddQuickReservation
  },
  data: function (){
    return{
      cottage: '',
      num_rooms: 0,
      num_beds: 0,
      isModalVisible: false,
    }
    },
      mounted:function (){
      const sumFuncy = async (a,b) => a+b;

      axios
        .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/1")
        .then(response => (this.cottage = response.data,this.num_rooms=response.data.rooms.length,response.data.rooms.forEach(async (room) => {this.num_beds=await sumFuncy(this.num_beds,room.bedNumber)})))

    },methods: {
      showModal:function() {
        this.isModalVisible = true;
      },
      closeModal:function() {
        this.isModalVisible = false;
      },
      deleteCottage:function (){
        let id = this.cottage.id
        axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/cottages/deleteCottage/"+id)
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
  .small-icon{
    font-size: 15px;
  }
</style>
