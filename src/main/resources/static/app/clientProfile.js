Vue.component("client-profile", {
    template: `

   <div class="row py-5 px-auto">
      <div class="col-md-8 mx-auto">
        <div class="bg-white shadow rounded overflow-hidden">
            <div class="px-4 pt-0 pb-4 cover">
                <div class="media align-items-end profile-head">
                    <div class="profile mr-3"><img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="..." width="150" class="rounded mb-2 img-thumbnail">
                    </div>
                    <div class="pb-4">
                    <h4 class="mt-2 mb-0" style="color: white; float:left; padding-left: 5px" >John Doe</h4>
                    <a 
                    <a href="#" class="btn flow delete-btn">Delete profile</a>
                    </div>
                    <div class="media-body mb-5 text-white">

                    </div>
                </div>
            </div>
            
              
            <div style="background-color: antiquewhite" class="container rounded mt-1">
            <div class="row">
                <div class="col-md-6 border-right">
                    <div class="p-3 py-3">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h6 class="text-right">Personal data</h6>
                        </div>
                        
                        <div class="row mt-2">
                            <div class="col-md-6 inputs"><label class="labels">Name</label><input type="text" class="form-control" placeholder="first name"  readonly value="John"></div>
                            <div class="col-md-6 inputs"><label class="labels">Surname</label><input type="text" class="form-control" readonly value="Doe" placeholder="Doe"></div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-12 inputs"><label class="labels">Email</label><input type="text" class="form-control" placeholder="email" readonly value="ivanaj0610@gmail.com"></div>
                            <div class="col-md-12 inputs"><label class="labels">Phone number</label><input type="text" class="form-control" placeholder="phone number" readonly value="0654079380"></div>
                            <div class="col-md-12 inputs"><label class="labels">Address</label><input type="text" class="form-control" placeholder="address" readonly value="Strazilovska 14"></div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-12 inputs"><label class="labels">Country</label><input type="text" class="form-control" placeholder="country" readonly value="USA"></div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-md-6 inputs"><label class="labels">City</label><input type="text" class="form-control" readonly value="Boston" placeholder="city"></div>
                            <div class="col-md-6 inputs"><label class="labels">Postal code</label><input type="text" class="form-control" placeholder="postal code" readonly value="21000"></div>
                        </div>
                      
                        <div class="mt-3 text-right"><button class="btn btn-primary edit-button" type="button">\edit</button></div>
                    </div>
                </div>
                
                <div class="col-md-6 border-right">
                
                    <div class="p-3 py-3">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h6>Loyalty program</h6>
                        </div>
                        
                         <div class="d-flex justify-content-between align-items-center mb-3">
                            <h7>&#127941; Status: Regular </h7>
                            <h7>&#11088; Points: 10 </h7>
                            <h7>&#10060; Penalties: 0</h7>
                        </div>
                        
                        <div class="d-flex justify-content-between align-items-center mb-3 mt-5">
                            <p><i>What is loyalty program? You get points with each reservation you make. Collect points to gain
                            a certain user status which brings you 
                            special benefits.
                            </i></p>
                        </div>

                         <div style="background: #ecd9c6; border:2px solid #ecd9c6" class="d-flex justify-content-between align-items-center mb-3 mt-3">
                             <h5> &#127941;<span style="color: slategrey; padding-left: 5px;  margin-left: 10px">SILVER</span> </h5>
                             <ul>
                                 <li>Collect 20 points to unlock</li>
                                 <li>10% off for all reservations</li>
                             </ul> 
                         </div>
                        
                         <div style="background: #ecd9c6; border:2px solid #ecd9c6" class="d-flex justify-content-between align-items-center mt-3">
                             <h5> &#127941;<span style="color: gold; padding-left: 5px; margin-left: 10px">GOLD</span> </h5>
                             <ul>
                                 <li>Collect 50 points to unlock</li>
                                 <li>20% off for all reservations</li>
                                 <li>No penalties</li>
                             </ul> 
                         </div>
                      
                        
                        
                        
                    </div>
                </div>
            </div>
           
        </div>
      </div>
    </div>
    `
    }

);