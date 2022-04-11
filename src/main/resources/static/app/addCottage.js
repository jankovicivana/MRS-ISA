Vue.component("add-cottage", {
    template: `
        <section class="vh-80" style="background-image: url('../cottageBack.jpg'); background-size: 100% 100%;">
        <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
            <div class="container h-100" >
                <div class="row d-flex justify-content-center align-items-center h-100" >
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
                        <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">ADD NEW COTTAGE?</h2>
                                <form @submit="addAdventure()">
                                   
                                    <div class="form-outline mb-4">
                                        <label class="label" for="name_input"> <i class="fas fa-home"></i>  Cottage name:</label>
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
                                        <label class="form-label" for="max_person_input"><i class="fas fa-door-open"></i> Add room: </label>
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
                              </div>
                        
                               
                                      
                                              
                                <br/>    
                                
                                <hr/>
                                <h4>Images</h4>
                                <div class="form-outline mb-4">
                                    <div class="form-outline mb-4">
                                            <label class="form-label" for="image_input"><i class="fas fa-pen"></i> Choose image: </label>
                                            <input type="text" id="image_input" class="form-control form-control-lg" />
                                     </div>
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
});