<template>
  <section class="vh-80 update_adventure" >
    <fishing-instructor-navbar></fishing-instructor-navbar>
    <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
      <div class="container h-100" >
        <div class="row d-flex justify-content-center align-items-center h-100" >
          <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
            <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
              <div class="card-body p-5">
                <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">Update adventure</h2>

                <form>

                  <div class="form-outline mb-4">
                    <label class="label" for="name_input"> <font-awesome-icon icon="fa-solid fa-fish"/>  Adventure name:</label>
                    <div>
                      <input type="text" id="name_input" ref="name_input" class="form-control form-control-lg" v-model="adventure.name" required />

                    </div>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="price_input"><font-awesome-icon icon="fa-solid fa-dollar-sign"/>  Price:</label>
                    <input type="number" id="price_input" ref="price_input" class="form-control form-control-lg" min="1"  v-model="adventure.price" required/>
                  </div>


                  <div class="form-outline mb-4">
                    <label class="form-label" for="max_person_input"><font-awesome-icon icon="fa-solid fa-user-friends"/> Max person number: </label>
                    <input type="number" id="max_person_input" ref="max_person_input"  class="form-control form-control-lg" min="1" v-model="adventure.maxPersonNum" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="cancel_fee_input"><font-awesome-icon icon="fa-solid fa-percentage"/> Cancel fee: </label>
                    <input type="number" id="cancel_fee_input" ref="cancel_fee_input" class="form-control form-control-lg" min="0.0" v-model="adventure.cancelFee" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="description_input"><font-awesome-icon icon="fa-solid fa-pen"/> Description: </label>
                    <textarea type="text" id="description_input" ref="description_input" class="form-control form-control-lg" v-model="adventure.description" required/>
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
                    <div class="mx-1 mb-2 row" v-for="rule in adventure.rules" id="rules" style="background: #e6e6e6;border-radius: 3%">
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
                    <div class="mx-1 mb-2 row" v-for="equip in adventure.fishingEquipment" id="fishingEquipment" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{equip.equipment}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeEquipment(equip.id)">Remove</a></p>
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
                    <div class="mx-1 mb-2 row" v-for="add in adventure.additionalServices" id="services" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{add.name}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeAdditionalService(add.id)">Remove</a></p>
                    </div>
                  </div>

                  <br/>

                  <hr/>
                  <h4>Images <font-awesome-icon class="small-icon" icon="fa-solid fa-camera"/></h4>
                  <div class="form-outline mb-2 mt-3">
                    <input type="file" id="image_input" ref="image_input" @change="onFileSelected" class="form-control" accept="image/png, image/gif, image/jpeg" multiple />
                  </div>
                  <div class="mx-1 mb-2 row" v-for="image in adventure.images" id="photos" style="background: #e6e6e6;border-radius: 3%">
                    <p class="col-8">{{image.path}}</p>
                    <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeImage(image.id)">Remove</a></p>
                  </div>

                  <button type="button" id="add_img_btn" class="btn" v-on:click="addImage()" style="background-color: #04414d; color: white;margin-left: 80%">Add image</button>


                  <br/>

                  <div class="d-flex justify-content-center">
                    <button type="button" v-on:click="updateAdventure()"  class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Save</div></button>
                  </div>


                </form>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import FishingInstructorNavbar from "./header/FishingInstructorNavbar";
import router from "../router";

export default {
  name: "UpdateAdventure",
  components: {FishingInstructorNavbar},
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/adventures/2", {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
      .then(response => (this.adventure = response.data,this.address = this.adventure.address))

  },data: function (){
    return{
      adventure: '',
      address : '',
    }
  },
  props: ['id']
  ,
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
        axios.post(process.env.VUE_APP_SERVER_PORT+"/api/images/addImage", {data:e.target.result,path:"../images/"+file.name,entityId:2}, {headers: {Authorization:
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
      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/rules/addRule", {rule:ruleText,entityId:2}, {headers: {Authorization:
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

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/additionalServices/addAdditionalService", {name:service,entityId:2}, {headers: {Authorization:
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
      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/equipment/addFishingEquipment", {equipment:enteredEquipment,adventureId:2,boatId : -1}, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added equipment!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

      document.getElementById('equip_input').value="";

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
    updateAdventure: function (){

      this.info = {
        id:this.adventure.id,
        name: this.adventure.name,
        price:this.adventure.price,
        maxPersonNum:this.adventure.maxPersonNum,
        description:this.adventure.description,
        address:this.address,
        cancelFee:this.adventure.cancelFee,
        rules: this.adventure.rules,
        fishingEquipment: this.adventure.fishingEquipment,
        additionalServices: this.adventure.additionalServices,
        images: this.adventure.images
      };

      for (let i of [this.adventure.name,this.adventure.description,this.address.country,this.address.city,this.address.street]){
        if (i===''){
          this.show('foo-css', 'error',`<p style="font-size: 25px">Warning!</p>`,`<p style="font-size: 20px">You must enter all info!</p>`)
          return;
        }
      }
      axios.put(process.env.VUE_APP_SERVER_PORT+"/api/adventures/updateAdventure",this.info, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully updated!</p>`, `<p style="font-size: 20px">Successfully updated adventure!</p>`)
          router.push({ name: 'AdventureProfile',params:{id:this.adventure.id}});
        }).catch(function error(error) {
        alert(error.response.data);
      });

    }

  }
}
</script>

<style scoped>

</style>
