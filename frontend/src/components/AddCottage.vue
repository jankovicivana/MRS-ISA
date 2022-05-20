<template>
  <section class="vh-80" style="background-image: url('https://images.unsplash.com/photo-1470770841072-f978cf4d019e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80'); background-size: 100% 100%;">
    <CottageOwnerNavbar></CottageOwnerNavbar>
    <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
      <div class="container h-100" >
        <div class="row d-flex justify-content-center align-items-center h-100" >
          <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
            <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
              <div class="card-body p-5">
                <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">ADD NEW COTTAGE?</h2>
                <form>

                  <div class="form-outline mb-4">
                    <label class="label" for="name_input"> <font-awesome-icon icon="fa-solid fa-home"></font-awesome-icon>  Cottage name:</label>
                    <div>
                      <input type="text" id="name_input"  ref="name_input" class="form-control form-control-lg"  required />

                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="form-label" for="price_input"><font-awesome-icon icon="fa-solid fa-dollar-sign"></font-awesome-icon>  Price:</label>
                    <input type="number" id="price_input" ref="price_input" class="form-control form-control-lg" min="1" required/>
                  </div>
                  <div class="form-outline mb-4">
                    <label class="form-label" for="max_person_input"><font-awesome-icon icon="fa-solid fa-user"></font-awesome-icon> Max person number: </label>
                    <input type="number" id="max_person_input" ref="max_person_input"  class="form-control form-control-lg" min="1" required/>
                  </div>
                  <div class="form-outline mb-4" style="margin-right: 150px">
                    <label class="form-label" for="num_bed_input"><font-awesome-icon icon="fa-solid fa-door-open"></font-awesome-icon> Add room: (Enter the number of beds)</label>
                    <div class="row">
                      <input type="number" id="num_bed_input"  ref="num_bed_input" class="input_add form-control-lg col-6 mx-3" min="1"/>
                      <button type="button"  v-on:click="addRoom()" class="btn col-3" style="background-color: #04414d;color: white">Add room</button>
                    </div>
                  </div>
                  <div class="mx-1 mb-2 row" id="rooms" style="background: #e6e6e6;border-radius: 3%"></div>


                  <div class="form-outline mb-4">
                    <label class="form-label" for="description_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Description: </label>
                    <input type="text" id="description_input" ref="description_input" class="form-control form-control-lg" required/>
                  </div>

                  <hr />
                  <h4>Address</h4>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="country_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Country: </label>
                    <input type="text" id="country_input" ref="country_input" class="form-control form-control-lg" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="city_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> City: </label>
                    <input type="text" id="city_input"  ref="city_input" class="form-control form-control-lg" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="street_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Street: </label>
                    <input type="text" id="street_input"  ref="street_input" class="form-control form-control-lg" required/>
                  </div>

                  <div class="form-outline mb-4">
                    <label class="form-label" for="postal_code_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Postal code: </label>
                    <input type="number" id="postal_code_input" ref="postal_code_input" class="form-control form-control-lg" required/>
                  </div>
                  <hr />
                  <h4>Conduct rulesss</h4>
                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="rule_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Rule: </label>
                      <div class="row">
                        <input type="text" id="rule_input" ref="rule_input" class="input_add form-control-lg col-6 mx-3" />
                        <button type="button" id="add_rule_btn" v-on:click="addRule()" class="btn col-2" style="background-color: #04414d;color: white">Add rule</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" id="rules" style="background: #e6e6e6;border-radius: 3%"></div>
                  </div>

                  <h4>Additional services</h4>

                  <div class="form-outline mb-4">
                    <div class="form-outline mb-4">
                      <label class="form-label" for="add_service_input"><font-awesome-icon icon="fa-solid fa-pen"></font-awesome-icon> Additional service: </label>
                      <div class="row">
                        <input type="text" id="add_service_input" ref="add_service_input" class="input_add form-control-lg col-6 mx-3" />
                        <button type="button" v-on:click="addService()" class="btn col-3" style="background-color: #04414d;color: white">Add service</button>
                      </div>
                    </div>
                    <div class="mx-1 mb-2 row" id="services" style="background: #e6e6e6;border-radius: 3%"></div>
                  </div>




                  <br/>

                  <hr/>
                  <h4>Images <font-awesome-icon icon="fa-solid fa-camera"></font-awesome-icon></h4>
                  <div class="form-outline mb-2 mt-3">
                    <input type="file" id="image_input" ref="image_input"  @change="onFileSelected" class="form-control" accept="image/png, image/gif, image/jpeg" multiple />
                  </div>
                  <div class="mx-1 mb-2 row" id="photos" style="background: #e6e6e6;border-radius: 3%">
                  </div>

                  <button type="button" id="add_img_btn" class="btn" v-on:click="addImage()" style="background-color: #04414d;color: white;margin-left: 80%">Add image</button>


                  <br/>
                  <div class="d-flex justify-content-center">
                    <button type="submit" v-on:click="addCottage()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add cottage</div></button>
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
  name: "AddCottage"
  ,
  components: {CottageOwnerNavbar},
  data: function (){
    return{
      rooms: [],
      images: [],
      rules: [],
      services: [],
      selectedFile: null
    }
  }
  ,
  methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfully added!</p>`
      let text = `<p style="font-size: 20px">Successfully added cottage!</p>`
      this.$notify({group, title, text, type})
    },

    onFileSelected: function (event){
      this.selectedFile = event.target.files[0];
    },

    addImage: function (){
      let img = this.$refs.image_input.value
      let file = document.querySelector('input[type=file]').files[0];
      var picturePath  = new FileReader();


      picturePath.readAsDataURL(file)
      picturePath.onload = e => {
        this.images.push(e.target.result);

      }

      if(img == ""){
        alert("Must choose file!");
        return;
      }


      let tag,aptag;
      [tag,aptag] = this.formElement(img,this.images);

      document.getElementById("photos").appendChild(tag);
      document.getElementById("photos").appendChild(aptag);
      document.getElementById('image_input').value="";

    },
    addRule: function (){
      let rule = this.$refs.rule_input.value
      if(rule == ""){
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
    addService: function (){
      let service = this.$refs.add_service_input.value
      if(service == ""){
        alert("Must enter additional service!!");
        return;
      }
      this.services.push(service);

      let tag,aptag;
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
    addRoom: function (){

      let room = this.$refs.num_bed_input.value
      if(room == ""){
        alert('Must enter bed number!')
        return;
      }

      this.rooms.push(room);

      var tag = document.createElement("p")
      tag.classList.add('col-8');

      var noun="";
      if (room==1){
        noun = " bed.";
      }else{
        noun = " beds.";
      }
      var text = document.createTextNode("Room with "+room+noun);
      tag.append(text);
      var aptag = document.createElement("p")
      var atag = document.createElement("a")
      atag.onclick = (function (p){
        return function (e){
          var index = p.indexOf(room);
          p.splice(index,1);
          tag.remove();
          aptag.remove();
          atag.remove();
        };
      }(this.rooms));
      atag.href = "javascript:void(0)";
      atag.style.textDecoration = 'none';
      atag.append(document.createTextNode("   Remove"))
      aptag.style.textAlign='right';
      aptag.classList.add('col-4');
      aptag.appendChild(atag);
      document.getElementById("rooms").appendChild(tag);
      document.getElementById("rooms").appendChild(aptag);
      document.getElementById('num_bed_input').value="";

    },
    addCottage: function (){
      console.log('doslo ovde')

      let name = this.$refs.name_input.value
      let description = this.$refs.description_input.value
      let maxPersonNum = this.$refs.max_person_input.value
      let price = this.$refs.price_input.value
      let country = this.$refs.country_input.value
      let city = this.$refs.city_input.value
      let street = this.$refs.street_input.value
      let postal_code = this.$refs.postal_code_input.value

      this.info = {
        name: name,
        price:price,
        maxNumPerson:maxPersonNum,
        description:description,
        country:country,
        city:city,
        street: street,
        postalCode: postal_code,
        rules: this.rules,
        rooms: this.rooms,
        additionalServices: this.services,
        images: this.images
      };

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/cottages/addCottage",this.info)
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

</style>
