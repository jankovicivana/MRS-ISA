<template>
  <section class="vh-80 update_cottage">
    <CottageOwnerNavbar></CottageOwnerNavbar>
    <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
      <div class="container h-100" >
        <div class="row d-flex justify-content-center align-items-center h-100" >
          <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
            <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
              <div class="card-body p-5">
                <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">UPDATE COTTAGE</h2>
                <form>

                  <div class="form-outline mb-4">
                    <label class="label" for="name_input"> <font-awesome-icon icon="fa-solid fa-home"></font-awesome-icon>  Cottage name:</label>
                    <div>
                      <input type="text" id="name_input" ref="name_input" class="form-control form-control-lg"  v-model="cottage.name"/>

                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="form-label" for="price_input"><font-awesome-icon icon="fa-solid fa-dollar-sign"></font-awesome-icon>  Price:</label>
                    <input type="number" id="price_input" ref="price_input" class="form-control form-control-lg" v-model="cottage.price" min="1"/>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="form-label" for="max_person_input"><font-awesome-icon icon="fa-solid fa-user"></font-awesome-icon> Max person number: </label>
                    <input type="number" id="max_person_input" ref="max_person_input" class="form-control form-control-lg" v-model="cottage.maxNumPerson" min="1"/>
                  </div>
                  <div class="form-outline mb-4" style="margin-right: 150px">
                    <label class="form-label" for="num_bed_input"><font-awesome-icon icon="fa-solid fa-door-open"></font-awesome-icon> Add room: (Enter the number of beds)</label>
                    <div class="row">
                      <input type="number" id="num_bed_input" ref="num_bed_input" class="input_add form-control-lg col-6 mx-3" min="1"/>
                      <button type="submit"  v-on:click="addRoom()" class="btn col-3" style="background-color: #04414d;color: white">Add room</button>
                    </div>
                  </div>
                  <div class="mx-1 mb-1 row" v-for="r in cottage.rooms" id="rooms" style="background: #e6e6e6;border-radius: 3%">
                    <p class="col-8">Room with {{r.bedNumber}} beds</p>
                    <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeRoom(r.id)">Remove</a></p>
                  </div>


                  <div class="form-outline mb-4 mt-3">
                    <label class="form-label" for="description_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Description: </label>
                    <textarea id="description_input" ref="description_input" class="form-control-lg" v-model="cottage.description" style="width: 100%;border: white"/>
                  </div>

                  <hr />
                  <h4>Address</h4>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="country_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Country: </label>
                    <input type="text" id="country_input" ref="country_input" class="form-control form-control-lg" v-model="this.address.country" />
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="city_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> City: </label>
                    <input type="text" id="city_input" ref="city_input" class="form-control form-control-lg" v-model="this.address.city" />
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="street_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Street: </label>
                    <input type="text" id="street_input" ref="street_input" class="form-control form-control-lg" v-model="this.address.street" />
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="postal_code_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Postal code: </label>
                    <input type="number" id="postal_code_input" ref="postal_code_input" class="form-control form-control-lg" v-model="this.address.postalCode" />
                  </div>
                  <hr />
                  <h4>Conduct rules</h4>
                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="rule_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Rule: </label>
                      <div class="row">
                        <input type="text" id="rule_input" ref="rule_input" class="input_add form-control-lg col-6 mx-3" />
                        <button type="submit" id="add_rule_btn" v-on:click="addRule()" class="btn col-2" style="background-color: #04414d;color: white">Add rule</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" v-for="rule in cottage.rules" id="rules" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{rule.rule}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeRule(rule.id)">Remove</a></p>
                    </div>
                  </div>

                  <h4>Additional services</h4>

                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="add_service_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Additional service: </label>
                      <div class="row">
                        <input type="text" id="add_service_input" ref="add_service_input" class="input_add form-control-lg col-6 mx-3" />
                        <button type="submit" v-on:click="addService()" class="btn col-3" style="background-color: #04414d;color: white">Add service</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" v-for="add in cottage.additionalServices" id="services" style="background: #e6e6e6;border-radius: 3%">
                      <p class="col-8">{{add.name}}</p>
                      <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeAdditionalService(add.id)">Remove</a></p>
                    </div>
                  </div>

                  <br/>

                  <hr/>
                  <h4>Images <font-awesome-icon icon="fa-solid fa-camera"></font-awesome-icon></h4>
                  <div class="form-outline mb-2 mt-3">
                    <input type="file" id="image_input" ref="image_input" @change="onFileSelected" class="form-control" accept="image/png, image/gif, image/jpeg" multiple />
                  </div>
                  <div class="mx-1 mb-2 row" v-for="image in cottage.images" id="photos" style="background: #e6e6e6;border-radius: 3%">
                    <p class="col-8">{{image.path}}</p>
                    <p class="col-4" style="text-align: right"><a href="javascript:void(0)" style="text-decoration: none" v-on:click="removeImage(image.id)">Remove</a></p>
                  </div>

                  <button type="submit" id="add_img_btn" class="btn" v-on:click="addImage()" style="background-color: #04414d;color: white;margin-left: 80%">Add image</button>


                  <br/>
                  <div class="d-flex justify-content-center">
                    <button type="submit" v-on:click="updateCottage()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Edit cottage</div></button>
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
import CottageOwnerNavbar from "./header/CottageOwnerNavbar";

export default {
  name: "UpdateCottage",
  components: {CottageOwnerNavbar},
  mounted: function (){
    axios
      .get(process.env.VUE_APP_SERVER_PORT+"/api/cottages/1")
      .then(response => (this.cottage = response.data,this.address = this.cottage.address))

  },data: function (){
    return{
      cottage: '',
      address : '',
    }
  }
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
        //this.cottage.images.push({data:e.target.result,path:file.name,entityId:1});
        axios.post(process.env.VUE_APP_SERVER_PORT+"/api/images/addImage", {data:e.target.result,path:"../images/"+file.name,entityId:1})
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
      //this.cottage.rules.push({rule:ruleText});

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/rules/addRule", {rule:ruleText,entityId:1})
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

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/additionalServices/addAdditionalService", {name:service,entityId:1})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added service!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

      document.getElementById('add_service_input').value="";

    },
    addRoom: function (){

      let room = this.$refs.num_bed_input.value
      if(room === ""){
        alert('Must enter bed number!')
        return;
      }

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/rooms/addRoom", {bedNumber:room,entityId:1})
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully added!</p>`, `<p style="font-size: 20px">Successfully added room!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

      document.getElementById('num_bed_input').value="";

    },
    removeRule:function (id){

      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/rules/deleteRule/"+id)
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed rule!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },removeRoom:function (id){
      console.log(id)

      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/rooms/deleteRoom/"+id)
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed room!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },removeAdditionalService:function (id){

      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/additionalServices/deleteAdditionalService/"+id)
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed additional service!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },removeImage:function (id){

      axios.delete(process.env.VUE_APP_SERVER_PORT+"/api/images/deleteImage/"+id)
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully removed!</p>`, `<p style="font-size: 20px">Successfully removed image!</p>`)
          setTimeout(() => {location.reload(); }, 1500)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    },
    updateCottage: function (){
      this.info = {
        id:this.cottage.id,
        name: this.cottage.name,
        price:this.cottage.price,
        maxNumPerson:this.cottage.maxNumPerson,
        description:this.cottage.description,
        address:this.address,
        rules: this.cottage.rules,
        rooms: this.cottage.rooms,
        additionalServices: this.cottage.additionalServices,
        images: this.cottage.images
      };

      axios.put(process.env.VUE_APP_SERVER_PORT+"/api/cottages/updateCottage",this.info)
        .then(response => {
          this.show('foo-css', 'success', `<p style="font-size: 25px">Successfully updated!</p>`, `<p style="font-size: 20px">Successfully updated cottage!</p>`)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    }

  }
}
</script>

<style scoped>

</style>
