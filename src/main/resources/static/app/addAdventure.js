Vue.component("add-adventure",{
    template:`
        <section class="vh-80" style="background-image: url('https://images.unsplash.com/photo-1551606292-9649254815d6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1188&q=80'); background-size: 100% 100%;">
        <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
            <div class="container h-100" >
                <div class="row d-flex justify-content-center align-items-center h-100" >
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
                        <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">New fishing adventure?</h2>

                                <form>
                                   
                                    <div class="form-outline mb-4">
                                        <label class="label" for="name_input"> <i class="fas fa-fish"></i>  Adventure name:</label>
                                        <div>
                                        <input type="text" id="name_input" class="form-control form-control-lg"  required />
                                             
                                        </div>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="price_input"><i class="fas fa-dollar-sign"></i>  Price:</label>
                                        <input type="number" id="price_input" class="form-control form-control-lg" min="0.01" required/>
                                    </div>


                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="max_person_input"><i class="fas fa-user"></i> Max person number: </label>
                                        <input type="number" id="max_person_input" class="form-control form-control-lg" min="1" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="cancel_fee_input"><i class="fas fa-percentage"></i> Cancel fee: </label>
                                        <input type="number" id="cancel_fee_input" class="form-control form-control-lg" min="0.0" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="description_input"><i class="fas fa-pen"></i> Description: </label>
                                        <textarea type="text" id="description_input" class="form-control form-control-lg" required/>
                                    </div>
                                     
                                     <hr />
                                    <h4>Address</h4>

                                    
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="country_input"><i class="fas fa-pen"></i> Country: </label>
                                        <input type="text" id="country_input" class="form-control form-control-lg" required/>
                                    </div>
                                    
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="city_input"><i class="fas fa-pen"></i> City: </label>
                                        <input type="text" id="city_input" class="form-control form-control-lg" required/>
                                    </div>
                                    
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="street_input"><i class="fas fa-pen"></i> Street: </label>
                                        <input type="text" id="street_input" class="form-control form-control-lg" required/>
                                    </div>
                                    
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="postal_code_input"><i class="fas fa-pen"></i> Postal code: </label>
                                        <input type="number" id="postal_code_input" class="form-control form-control-lg" required/>
                                    </div>

                            <hr />
                            <h4>Conduct rules</h4>

  
                              <div class="form-outline mb-4">
                                    <div class="form-outline mb-4">
                                            <label class="form-label" for="rule_input"><i class="fas fa-pen"></i> Rule: </label>
                                                <div class="row">
                                                    <input type="text" id="rule_input" class="input_add form-control-lg col-8 mx-3" style="border-color:white;" />
                                                    <button type="button" id="add_rule_btn" v-on:click="addRule()" class="btn col-3" style="background-color: #04414d;color: white">Add rule</button>
                                                </div>                                    
                                    </div>
                                    <div class="mx-1 mb-2 row" id="rules" style="background: #e6e6e6;border-radius: 3%"></div>
                              </div>
                        
                               
                                
                                <hr/>
                                <h4>Fishing equipment</h4>
                                <div class="form-outline mb-4">
                                    <div class="form-outline mb-4">
                                            <label class="form-label" for="equip_input"><i class="fas fa-pen"></i> Equipment: </label>
                                                <div class="row">
                                                    <input type="text" id="equip_input" class="input_add form-control-lg col-8 mx-3"  style="border-color:white;" />
                                                    <button type="button" id="add_equip_btn" v-on:click="addEquipment()" class="btn col-3" style="background-color: #04414d;color: white">Add equipment</button>
                                                </div>
                                    </div>
                                    <div class="mx-1 mb-2 row" id="equipmentList" style="background: #e6e6e6;border-radius: 3%"></div>

                               </div>   
                                              
                                <br/>    
                                <h4>Additional services</h4>
  
                              <div class="form-outline mb-4">
                                    <div class="form-outline mb-4">
                                            <label class="form-label" for="add_service_input"><i class="fas fa-pen"></i> Additional service: </label>
                                            <div class="row">
                                                <input type="text" id="add_service_input" class="input_add form-control-lg col-8 mx-3"   style="border-color:white;" />
                                                <button type="button" v-on:click="addService()" class="btn col-3" style="background-color: #04414d;color: white">Add service</button>
                                            </div>
                                     </div>
                                     <div class="mx-1 mb-2 row" id="services" style="background: #e6e6e6;border-radius: 3%"></div>
                              </div>
                              
                                <br/>
                              
                                <hr/>
                                <h4>Images<i class="fas fa-camera px-2"></i></h4>
                                <div class="form-outline mb-2 mt-3">
                                     <input type="file" id="image_input" @change="onFileSelected" class="form-control" accept="image/png, image/gif, image/jpeg" multiple />
                                </div>
                                <div class="mx-1 mb-2 row" id="photos" style="background: #e6e6e6;border-radius: 3%">
                                </div>
                               
                               <button type="button" id="add_img_btn" class="btn" v-on:click="addImage()" style="background-color: #04414d; color: white;margin-left: 80%">Add image</button>
   
                                                                                      
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
    `
    ,data: function (){
        return{
            imagesUrls: [],
            rules: [],
            services: [],
            equipment: [],
            selectedFile: null
        }
    }
    ,
    methods:{
        onFileSelected: function (event){
            this.selectedFile = event.target.files[0];
        },

        addImage: function (){
            let img = $("#image_input").val()
            let file = document.querySelector('input[type=file]').files[0];
            var picturePath  = new FileReader();


            picturePath.readAsDataURL(file)
            picturePath.onload = e => {
                this.imagesUrls.push(e.target.result);

            }

            if(img == ""){
                alert("Must choose file!");
                return;
            }
            [tag,aptag] = this.formElement(img,this.imagesUrls);

            document.getElementById("photos").appendChild(tag);
            document.getElementById("photos").appendChild(aptag);
            document.getElementById('image_input').value="";

        },
        addRule: function (){
            let rule = $('#rule_input').val();
            if(rule == ""){
                alert("Must enter rule!");
                return;
            }
            this.rules.push(rule);
            [tag,aptag] = this.formElement(rule,this.rules);

            document.getElementById("rules").appendChild(tag);
            document.getElementById("rules").appendChild(aptag);
            document.getElementById('rule_input').value="";

        },
        addEquipment: function (){
            let equip = $('#equip_input').val();
            if(equip == ""){
                alert("Must enter equipment!");
                return;
            }
            this.equipment.push(equip);
            [tag,aptag] = this.formElement(equip,this.equipment);

            document.getElementById("equipmentList").appendChild(tag);
            document.getElementById("equipmentList").appendChild(aptag);
            document.getElementById('equip_input').value="";

        },

        addService: function (){
            let service = $('#add_service_input').val();
            if(service == ""){
                alert("Must enter additional service!!");
                return;
            }
            this.services.push(service);
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
            console.log('doslo ovde')

            let name = $("#name_input").val();
            let description = $("#description_input").val();
            let max_person_num = $("#max_person_input").val();
            let price = $("#price_input").val();
            let country = $("#country_input").val();
            let city = $("#city_input").val();
            let street = $("#street_input").val();
            let postal_code = $("#postal_code_input").val();
            let cancel_fee = $("#cancel_fee_input").val();

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

            axios.post("api/adventures/addAdventure",this.info)
                .then(response => {
                    alert("Addition is successfull!")
                }).catch(function error(error) {
                alert(error.response.data);
            });

        }

    }
});