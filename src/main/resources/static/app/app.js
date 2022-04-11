const PostPage = { template: '<post-page></post-page>'}
const AdventureProfile = {template: '<adventure-profile></adventure-profile>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage},
        { path: '/adventureProfile', component: AdventureProfile}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});