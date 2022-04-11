const PostPage = { template: '<post-page></post-page>'}
const AddCottage = {template: '<add-cottage></add-cottage>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage},
        { path:'/addCottage',component: AddCottage}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});