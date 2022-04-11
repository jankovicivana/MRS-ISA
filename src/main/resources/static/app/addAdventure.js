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

                                <form @submit="addAdventure()">
                                   
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
                                        <input type="text" id="description_input" class="form-control form-control-lg" required/>
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
                                        <input type="text" id="postal_code_input" class="form-control form-control-lg" required/>
                                    </div>

                            <hr />
                            <h4>Conduct rules</h4>

  
                              <div class="form-outline mb-4">
                                    <div class="form-outline mb-4">
                                            <label class="form-label" for="rule_input"><i class="fas fa-pen"></i> Rule: </label>
                                            <input type="text" id="rule_input" class="form-control form-control-lg" />
                                     </div>
                                     <button class="button"  style="alignment: right" @click = "addRule()">Add rule</button>
                               </div>
                        
                               
                                
                                <hr/>
                                <h4>Fishing equipment</h4>
                                <div class="form-outline mb-4">
                                    <div class="form-outline mb-4">
                                            <label class="form-label" for="equip_input"><i class="fas fa-pen"></i> Equipment: </label>
                                            <input type="text" id="equip_input" class="form-control form-control-lg" />
                                     </div>
                                     <button class="button" style="alignment: right" @click = "addEquipment()">Add equipment</button>
                               </div>   
                                              
                                <br/>    
                                
                                <hr/>
                                <h4>Images</h4>
                                <div class="form-outline mb-4">
                                    <div class="form-outline mb-4">
                                            <label class="form-label" for="image_input"><i class="fas fa-pen"></i> Equipment: </label>
                                            <input type="text" id="image_input" class="form-control form-control-lg" />
                                     </div>
                                     <button class="button" style="alignment: right" @click = "addImage()">Add image</button>
                               </div>   
                                           
                                <br/>         

                                <div class="d-flex justify-content-center">
                                        <button type="submit"  class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add</div></button>
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
    ,
    methods:{
        addRule: function (){
            console.log("pozvan za rule");
            let rule = $("#rule_input").val();
            this.rules.push(rule);
            $("#rule_input").val("");
            alert("Rule is added.");
        },
        addEquipment: function (){
            console.log("pozvan za rule");
            let equipment = $("#equip_input").val();
            this.rules.push(equipment);
            $("#equip_input").val("");
            alert("Equipment is added.");
        },
        addImage: function (){
            console.log("pozvan za rule");
            let img = $("#image_input").val();
            this.rules.push(img);
            $("#image_input").val("");
            alert("Image is added.");
        },
        addAdventure: async function (){
             console.log("sdfsdf");
        let name = $("#name_input").val();
        let description = $("#description_input").val();
        let maxPersonNum = $("#max_person_input").val();
        let cancelFee = $("#cancel_fee_input").val();
        let price = $("#price_input").val();
            let country = $("#country_input").val();
            let city = $("#city_input").val();
            let street = $("#street_input").val();
            let postal_code = $("#postal_code_input").val();


        let info = {
            name:name,
            price:price,
            maxPersonNum:maxPersonNum,
            cancelFee: cancelFee,
            description:description,
            country:country,
            city:city,
            street: street,
            postal_code:postal_code

        }
        console.log("doslo ovjde");
        console.log((JSON.stringify(info)).toString());
        axios.post("/addAdventure",(JSON.stringify(info)).toString())
            .then(function response(data){
                alert("Addition is successfull");
            }).catch(function error(error) {
                alert(error.response.data);
            });
        }
    },
    mounted: function (){
        this.rules = [];
        this.equipment = [];
    }
});