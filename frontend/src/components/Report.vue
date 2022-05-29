<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <div class="modal"
           role="dialog"
           aria-labelledby="modalTitle"
           aria-describedby="modalDescription"
           style="background-image: url('https://images.unsplash.com/photo-1418065460487-3e41a6c84dc5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80'); background-size: 100% 100%;"
      >
        <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
          <div class="container h-100" >
            <div class="row d-flex justify-content-center align-items-center h-100" >
              <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
                <div class="card" style="border-radius: 15px; background: #ecd9c6;width: 1000px">
                  <div class="card-body p-5">
                    <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">Create report</h2>
                    <form>
                      <hr/>


                      <div class="form-check">
                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" v-on:click="toggleSanction()" checked>
                        <label class="form-check-label" for="flexRadioDefault1">
                          Comment:
                        </label>
                      </div>

                      <textarea type="text" class="form-control form-control-lg" ref="comment_input" id="comment_input" />

                      <div class="form-check pt-2">
                        <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                        <label class="form-check-label" for="flexCheckDefault">
                          Sanction the client (1 Penalty)
                        </label>
                      </div>

                      <hr/>
                      <div class="form-check pb-3">
                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" v-on:click="toggleClient()">
                        <label class="form-check-label" for="flexRadioDefault2">
                          Client did not show up (1 Penalty)
                        </label>
                      </div>


                      <div class="d-flex justify-content-center">
                        <button type="submit" v-on:click="createReport()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Send report</div></button>
                      </div>
                    </form>
                    <br/>

                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import axios from "axios";

export default {
  name: "Report",
  data: function (){
    return{
      clientId: ''
    }
  }
  ,methods:{
    show: function(group, type=''){
      let title = `<p style="font-size: 25px">Successfully!</p>`
      let text = `<p style="font-size: 20px">Successfully sent report!</p>`
      this.$notify({group, title, text, type})
    },
    createReport:function(){
      this.clientId = this.$route.params.id;
      console.log(this.clientId)

      this.info = {
        clientId: this.clientId,
        reportString: this.$refs.comment_input.value,
        didShowUp: document.getElementById('flexRadioDefault1').checked,
        sanction : document.getElementById('flexCheckDefault').checked
      }

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/clients/createReport/",this.info, {headers: {Authorization:
            'Bearer ' + sessionStorage.getItem("accessToken")}})
        .then(response => {
          this.show('foo-css', 'success')
          setTimeout(() => {this.$router.push({path:"/ReservationHistory"}); }, 3000)
        }).catch(function error(error) {
        alert(error.response.data);
      });




    },
    toggleSanction:function (){
      document.getElementById('comment_input').disabled = false;
      document.getElementById('flexCheckDefault').disabled = false;
    },
    toggleClient:function (){
      document.getElementById('comment_input').disabled = true;
      document.getElementById('flexCheckDefault').disabled = true;
    }
  }
}
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: #FFFFFF;
  box-shadow: 2px 2px 20px 1px;
  overflow-x: auto;
  display: flex;
  flex-direction: column;
}
</style>
