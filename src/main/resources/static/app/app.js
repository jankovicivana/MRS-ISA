const PostPage = { template: '<post-page></post-page>'}
const AddAdventure = {template: '<add-adventure></add-adventure>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage},
        { path: '/addAdventure', component: AddAdventure}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});