<template>
  <section class="vh-80 update_adventure ">
    <fishing-instructor-navbar></fishing-instructor-navbar>
    <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
      <div class="container h-100" >
        <div class="row d-flex justify-content-center align-items-center h-100" >
          <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
            <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
              <div class="card-body p-5">
                <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">New fishing adventure?</h2>

                <form>

                  <div class="form-outline mb-4">
                    <label class="label" for="name_input"> <font-awesome-icon icon="fa-solid fa-fish"/>  Adventure name:</label>
                    <div>
                      <input type="text" id="name_input" ref="name_input" class="form-control form-control-lg"  required />

                    </div>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="price_input"><font-awesome-icon icon="fa-solid fa-dollar-sign"/>  Price:</label>
                    <input type="number" id="price_input" ref="price_input" class="form-control form-control-lg" min="0" required/>
                  </div>


                  <div class="form-outline mb-4">
                    <label class="form-label" for="max_person_input"><font-awesome-icon icon="fa-solid fa-user-friends"/> Max person number: </label>
                    <input type="number" id="max_person_input" ref="max_person_input"  class="form-control form-control-lg" min="1" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="cancel_fee_input"><font-awesome-icon icon="fa-solid fa-percentage"/> Cancel fee: </label>
                    <input type="number" id="cancel_fee_input" ref="cancel_fee_input" class="form-control form-control-lg" min="0.0" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="description_input"><font-awesome-icon icon="fa-solid fa-pen"/> Description: </label>
                    <textarea type="text" id="description_input" ref="description_input" class="form-control form-control-lg" required/>
                  </div>

                  <hr />
                  <h4>Address</h4>


                  <div class="form-outline mb-4">
                    <label class="form-label" for="country_input"><font-awesome-icon icon="fa-solid fa-pen"/> Country: </label>
                    <input type="text" id="country_input" ref="country_input" class="form-control form-control-lg" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="city_input"><font-awesome-icon icon="fa-solid fa-pen"/> City: </label>
                    <input type="text" id="city_input" ref="city_input" class="form-control form-control-lg" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="street_input"><font-awesome-icon icon="fa-solid fa-pen"/> Street: </label>
                    <input type="text" id="street_input" ref="street_input" class="form-control form-control-lg" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="postal_code_input"><font-awesome-icon icon="fa-solid fa-pen"/> Postal code: </label>
                    <input type="number" id="postal_code_input" ref="postal_code_input" class="form-control form-control-lg" required/>
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
                    <div class="mx-1 mb-2 row" id="rules" style="background: #e6e6e6;border-radius: 3%"></div>
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
                    <div class="mx-1 mb-2 row" id="equipmentList" style="background: #e6e6e6;border-radius: 3%"></div>

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
                    <div class="mx-1 mb-2 row" id="services" style="background: #e6e6e6;border-radius: 3%"></div>
                  </div>

                  <br/>

                  <hr/>
                  <h4>Images <font-awesome-icon class="small-icon" icon="fa-solid fa-camera"/></h4>
                  <div class="form-outline mb-2 mt-3">
                    <input type="file" id="image_input" ref="image_input" @change="onFileSelected" class="form-control" accept="image/png, image/gif, image/jpeg" multiple />
                  </div>
                  <div class="mx-1 mb-2 row" id="photos" style="background: #e6e6e6;border-radius: 3%">
                  </div>

                  <button type="button" id="add_img_btn" class="btn" v-on:click="deleteClient(client)" style="background-color: #04414d; color: white;margin-left: 80%">Add image</button>


                  <br/>

                  <div class="d-flex justify-content-center">
                    <button type="submit" v-on:click="addAdventure()"  class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add</div></button>
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

export default {
  name: "AddAdventure",
  components: {FishingInstructorNavbar},
  data: function (){
    return{
      imagesUrls: [],
      rules: [],
      services: [],
      equipment: [],
      selectedFile: null
    }
  },
  methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfully added!</p>`
      let text = `<p style="font-size: 20px">Successfully added adventure!</p>`
      this.$notify({group, title, text, type})
    },
    onFileSelected: function (event){
      this.selectedFile = event.target.files[0];
    },

    addImage: function (){
      let img = this.$refs.image_input.value;
      let file = document.querySelector('input[type=file]').files[0];
      var picturePath  = new FileReader();


      picturePath.readAsDataURL(file)
      picturePath.onload = e => {
        this.imagesUrls.push(e.target.result);

      }

      if(img === ""){
        alert("Must choose file!");
        return;
      }
      let tag,aptag;
      [tag,aptag] = this.formElement(img,this.imagesUrls);

      document.getElementById("photos").appendChild(tag);
      document.getElementById("photos").appendChild(aptag);
      document.getElementById('image_input').value="";

    },
    addRule: function (){
      let rule = this.$refs.rule_input.value;
      if(rule === ""){
        alert("Must enter rule!");
        return;
      }
      this.rules.push(rule);
      let tag,aptag;
      [tag,aptag] = this.formElement(rule,this.rules);

      document.getElementById("rules").appendChild(tag);
      document.getElementById("rules").appendChild(aptag);
      document.getElementById('rule_input').value="";

    },
    addEquipment: function (){
      let equip = this.$refs.equip_input.value
      if(equip === ""){
        alert("Must enter equipment!");
        return;
      }
      let aptag,tag;
      this.equipment.push(equip);
      [tag,aptag] = this.formElement(equip,this.equipment);

      document.getElementById("equipmentList").appendChild(tag);
      document.getElementById("equipmentList").appendChild(aptag);
      document.getElementById('equip_input').value="";

    },

    addService: function (){
      let service = this.$refs.add_service_input.value;
      if(service === ""){
        alert("Must enter additional service!!");
        return;
      }
      this.services.push(service);
      let tag;
      let aptag;
      [tag,aptag] = this.formElement(service,this.services);
      document.getElementById("services").appendChild(tag);
      document.getElementById("services").appendChild(aptag);
      document.getElementById('add_service_input').value="";

    },
    formElement:function(element,elements){

      var tag = document.createElement("p")
      tag.classList.add('col-8');
      var text = document.createTextNode(element);
      tag.append(text);
      var aptag = document.createElement("p")
      var atag = document.createElement("a")
      atag.onclick = (function (p){
        return function (e){
          var index = p.indexOf(element);
          p.splice(index,1);
          tag.remove();
          aptag.remove();
          atag.remove();
        };
      }(elements));
      atag.href = "javascript:void(0)";
      atag.style.textDecoration = 'none';
      atag.append(document.createTextNode("   Remove"))
      aptag.style.textAlign='right';
      aptag.classList.add('col-4');
      aptag.appendChild(atag);
      return [tag,aptag];
    },
    addAdventure: function (){

      let name = this.$refs.name_input.value
      let description = this.$refs.description_input.value
      let max_person_num = this.$refs.max_person_input.value
      let price = this.$refs.price_input.value
      let country = this.$refs.country_input.value
      let city = this.$refs.city_input.value
      let street =this.$refs.street_input.value
      let postal_code = this.$refs.postal_code_input.value
      let cancel_fee = this.$refs.cancel_fee_input.value

      this.info = {
        name: name,
        price:price,
        maxPersonNum:max_person_num,
        description:description,
        country:country,
        city:city,
        street: street,
        postal_code: postal_code,
        cancelFee: cancel_fee,
        rules: this.rules,
        additionalServices: this.services,
        images: this.imagesUrls,
        fishingEquipment: this.equipment
      };

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/adventures/addAdventure",this.info, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success')
          setTimeout(() => {location.reload(); }, 3000)
        }).catch(function error(error) {
        alert(error.response.data);
      });

    }

  }
}
</script>

<style scoped>
.small-icon{
  font-size: 20px;
}
</style>
