<template>
  <div>
    <BoatOwnerNavbar></BoatOwnerNavbar>
  <section class="add_boat vh-80">
    <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
      <div class="container h-100" >
        <div class="row d-flex justify-content-center align-items-center h-100" >
          <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
            <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
              <div class="card-body p-5">
                <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">UPDATE BOAT</h2>

                <form>

                  <div class="form-outline mb-4">
                    <label class="label" for="name_input"> <font-awesome-icon icon="fa-solid fa-anchor"/>  Boat name:</label>
                    <div>
                      <input type="text" id="name_input" ref="name_input" class="form-control form-control-lg"  v-model="boat.name" required />

                    </div>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="price_input"><font-awesome-icon icon="fa-solid fa-sailboat"/>  Boat type:</label>
                    <select id="boat_type_input" ref="boat_type_input" class="form-control form-control-lg" v-model="boat.type" required>
                      <option>YACHT</option>
                      <option>SAIL BOAT</option>
                      <option>CANOE</option>
                      <option>ROW BOAT</option>
                      <option>MOTOR BOAT</option>
                    </select>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="price_input"><font-awesome-icon icon="fa-solid fa-dollar-sign"/>  Price:</label>
                    <input type="number" id="price_input" ref="price_input" class="form-control form-control-lg" v-model="boat.price" required/>
                  </div>


                  <div class="form-outline mb-4">
                    <label class="form-label" for="max_person_input"><font-awesome-icon icon="fa-solid fa-user-friends"/> Capacity: </label>
                    <input type="number" id="max_person_input" ref="max_person_input"  class="form-control form-control-lg" min="1" v-model="boat.capacity" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="cancel_fee_input"><font-awesome-icon icon="fa-solid fa-percentage"/> Cancel fee: </label>
                    <input type="number" id="cancel_fee_input" ref="cancel_fee_input" class="form-control form-control-lg" min="0.0" v-model="boat.cancelFee" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="description_input"><font-awesome-icon icon="fa-solid fa-pen"/> Description: </label>
                    <textarea type="text" id="description_input" ref="description_input" class="form-control form-control-lg" v-model="boat.description" required/>
                  </div>
                  <hr />

                  <h4>Boat characteristics</h4>
                  <div class="form-outline mb-4">
                    <label class="form-label" for="length_input"><font-awesome-icon icon="fa-solid fa-pen"/> Length (m): </label>
                    <input type="number" id="length_input" ref="length_input"  class="form-control form-control-lg" min="1" v-model="boat.length" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="motor_number_input"><font-awesome-icon icon="fa-solid fa-pen"/> Motor number: </label>
                    <input type="number" id="motor_number_input" ref="motor_number_input" class="form-control form-control-lg" v-model="boat.motorNum" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="power_input"><font-awesome-icon icon="fa-solid fa-pen"/> Power (HS): </label>
                    <input type="number" id="power_input" ref="power_input" class="form-control form-control-lg" min="1" v-model="boat.power" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="max_speed_input"><font-awesome-icon icon="fa-solid fa-pen"/> Max speed (km/h): </label>
                    <input type="number" id="max_speed_input" ref="max_speed_input" class="form-control form-control-lg" min="1" v-model="boat.maxSpeed" required/>
                  </div>

                  <hr />
                  <h4>Address</h4>


                  <div class="form-outline mb-4">
                    <label class="form-label" for="country_input"><font-awesome-icon icon="fa-solid fa-pen"/> Country: </label>
                    <input type="text" id="country_input" ref="country_input" class="form-control form-control-lg" v-model="address.country" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="city_input"><font-awesome-icon icon="fa-solid fa-pen"/> City: </label>
                    <input type="text" id="city_input" ref="city_input" class="form-control form-control-lg" v-model="address.city" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="street_input"><font-awesome-icon icon="fa-solid fa-pen"/> Street: </label>
                    <input type="text" id="street_input" ref="street_input" class="form-control form-control-lg" v-model="address.street" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="postal_code_input"><font-awesome-icon icon="fa-solid fa-pen"/> Postal code: </label>
                    <input type="number" id="postal_code_input" ref="postal_code_input" class="form-control form-control-lg" v-model="address.postalCode" required/>
                  </div>

                  <hr />
                  <h4>Conduct rules</h4>


                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="rule_input"><font-awesome-icon icon="fa-solid fa-pen"/> Rule: </label>
                      <div class="row">
                        <input type="text" id="rule_input" ref="rule_input" class="input_add form-control-lg col-8 mx-3" style="border-color:white;" />
                        <button type="button" id="add_rule_btn" v-on:click="addRule()" class="btn col-3" style="background-color: #04414d;color: white">Add rule</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" v-for="rule in boat.rules" id="rules" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{rule.rule}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeRule(rule.id)">Remove</a></p>
                    </div>

                  </div>



                  <hr/>
                  <h4>Fishing equipment</h4>
                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="equip_input"><font-awesome-icon icon="fa-solid fa-pen"/> Equipment: </label>
                      <div class="row">
                        <input type="text" id="equip_input" ref="equip_input" class="input_add form-control-lg col-8 mx-3"  style="border-color:white;" />
                        <button type="button" id="add_equip_btn" v-on:click="addEquipment()" class="btn col-3" style="background-color: #04414d;color: white">Add equipment</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" v-for="equip in boat.fishingEquipment" id="equipmentList" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{equip.equipment}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeEquipment(equip.id)">Remove</a></p>
                    </div>

                  </div>

                  <h4>Navigation equipment</h4>
                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="equip_input"><font-awesome-icon icon="fa-solid fa-pen"/> Equipment: </label>
                      <div class="row">
                        <select id="nav_equip_input" ref="nav_equip_input" class="input_add form-control-lg col-8 mx-3"  style="border-color:white;">
                          <option>GPS</option>
                          <option>RADAR</option>
                          <option>VHF RADIO</option>
                          <option>FISHFINDER</option>
                        </select>
                        <button type="button" id="nav_equip_input" v-on:click="addNavEquipment()" class="btn col-3" style="background-color: #04414d;color: white">Add equipment</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" v-for="n in boat.navigationEquipment" id="navEquipmentList" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{n.equipment}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeNavEquipment(n.id)">Remove</a></p>
                    </div>

                  </div>

                  <br/>
                  <h4>Additional services</h4>

                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="add_service_input"><font-awesome-icon icon="fa-solid fa-pen"/> Additional service: </label>
                      <div class="row">
                        <input type="text" id="add_service_input" ref="add_service_input" class="input_add form-control-lg col-8 mx-3"   style="border-color:white;" />
                        <button type="button" v-on:click="addService()" class="btn col-3" style="background-color: #04414d;color: white">Add service</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" v-for="a in boat.additionalServices" id="services" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{a.name}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeAdditionalService(a.id)">Remove</a></p>
                    </div>
                  </div>

                  <br/>

                  <hr/>
                  <h4>Images <font-awesome-icon class="small-icon" icon="fa-solid fa-camera"/></h4>
                  <div class="form-outline mb-2 mt-3">
                    <input type="file" id="image_input" ref="image_input" @change="onFileSelected" class="form-control" accept="image/png, image/gif, image/jpeg" multiple />
                  </div>
                  <div class="mx-1 mb-2 row" v-for="image in boat.images" id="photos" style="background: #e6e6e6;border-radius: 3%">
                    <p class="col-8">{{image.path}}</p>
                    <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeImage(image.id)">Remove</a></p>
                  </div>

                  <button type="button" id="add_img_btn" class="btn" v-on:click="addImage()" style="background-color: #04414d; color: white;margin-left: 80%">Add image</button>


                  <br/>

                  <div class="d-flex justify-content-center">
                    <button type="button" v-on:click="updateBoat()"  class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Update</div></button>
                  </div>


                </form>

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
import BoatOwnerNavbar from "./header/BoatOwnerNavbar";

export default {
  name: "UpdateBoat",
  components: {BoatOwnerNavbar},
  mounted:function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/boats/"+this.$route.params.id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (this.boat = response.data,this.address = this.boat.address,this.boat_type = this.boat.type))


    let elem = document.getElementById("boat_type_input")
    elem.value = this.boat_type;


  },data:function (){
    return{
      boat:'',
      address:'',
      boat_type:'',
      boatId:this.$route.params.id
    }
  },
  methods:{
    show: function(group, type='', title, text){
      this.$notify({group, title, text, type})
    },
    onFileSelected: function (event){
      this.selectedFile = event.target.files[0];
    },
    addImage: function (){
      let img = this.$refs.image_input.value
      if(img === ""){
        alert("Must choose file!");
        return;
      }
      let file = document.querySelector('input[type=file]').files[0];
      var picturePath  = new FileReader();


      picturePath.readAsDataURL(file)
      picturePath.onload = e => {
        axios.post(process.env.VUE_APP_SERVER_PORT+"/api/images/addImage", {data:e.target.result,path:file.name,entityId:this.boat.id}, {headers: {Authorization:
              'Bearer ' + sessionStorage.getItem("accessToken")}})
          .then(response => {
            this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added image!</p>`)
            setTimeout(() => {location.reload(); }, 1500)
          }).catch(function error(error) {
          alert(error.response.data);
        });
      }

      document.getElementById('image_input').value="";

    },
    addRule: function (){
      let ruleText = this.$refs.rule_input.value
      if(ruleText === ""){
        alert("Must enter rule!");
        return;
      }
      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/rules/addRule", {rule:ruleText,entityId:this.boat.id}, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added rule!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

      document.getElementById('rule_input').value="";

    },
    addService: function (){
      let service = this.$refs.add_service_input.value
      if(service === ""){
        alert("Must enter additional service!!");
        return;
      }
      //this.cottage.additionalServices.push(service);

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/additionalServices/addAdditionalService", {name:service,entityId:this.boat.id}, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added service!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

      document.getElementById('add_service_input').value="";

    },
    addEquipment: function (){

      let enteredEquipment = this.$refs.equip_input.value
      if(enteredEquipment === "" || enteredEquipment == null){
        alert('Must enter equipment!')
        return;
      }
      console.log(enteredEquipment)
      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/equipment/addFishingEquipment", {equipment:enteredEquipment,adventureId:-1,boatId : this.boat.id}, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added equipment!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

      document.getElementById('equip_input').value="";

    },addNavEquipment: function (){

      let enteredEquipment = this.$refs.nav_equip_input.value
      if(enteredEquipment == "" || enteredEquipment == null){
        alert('Must enter equipment!')
        return;
      }
      console.log(enteredEquipment)
      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/navigationEquipment/addNavigationEquipment", {equipment:enteredEquipment,boatId : this.boat.id}, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added equipment!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

      document.getElementById('nav_equip_input').value="";

    },
    removeRule:function (id){

      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/rules/deleteRule/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          console.log(id);
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed rule!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },removeEquipment:function (id){
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/equipment/deleteFishingEquipment/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed equipment!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },removeNavEquipment:function (id){
      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/navigationEquipment/deleteNavigationEquipment/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed equipment!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },removeAdditionalService:function (id){

      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/additionalServices/deleteAdditionalService/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed additional service!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },removeImage:function (id){

      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/images/deleteImage/"+id, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed image!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },
    updateBoat: function (){

      this.info = {
        id:this.boat.id,
        name: this.boat.name,
        price:this.boat.price,
        capacity:this.boat.capacity,
        description:this.boat.description,
        address:this.address,
        cancelFee:this.boat.cancelFee,
        rules: this.boat.rules,
        type: this.boat.type,
        length: this.boat.length,
        motorNum: this.boat.motorNum,
        power: this.boat.power,
        maxSpeed: this.boat.maxSpeed,
        fishingEquipment: this.boat.fishingEquipment,
        additionalServices: this.boat.additionalServices,
        navigationEquipment: this.boat.navigationEquipment,
        images: this.boat.images
      };

      axios.put(process.env.VUE_APP_SERVER_PORT+"/api/boats/updateBoat",this.info, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully updated!</p>`, `<p style="font-size: 20px">Successfully updated boat!</p>`)
          setTimeout(() => {this.$router.push({name:"BoatProfile",params:{id:this.boat.id}}); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    }
  }
}
</script>

<style scoped>

</style>
