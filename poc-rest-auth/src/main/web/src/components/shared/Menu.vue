<template>
  <nav class="navbar navbar-default">
  <div class="container-fluid">

    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Report</a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" v-if="loggedIn">
      <ul class="nav navbar-nav">
        <li v-for="route in routes">
          <router-link :to="route.path ? route.path : '/'">{{route.title}}</router-link>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
            {{username}} <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
            <li><a href="#" @click.prevent="logout">Sair</a></li>
          </ul>
        </li>
      </ul>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" v-else="loggedIn">
      <ul class="nav navbar-nav navbar-right">
        <li><router-link :to="'/login'">Login</router-link></li>
      </ul>
    </div>
  </div>
</nav>
</template>

<script>
import {routes} from '../../utils/routes'
import {SessionUtil} from '../../utils/SessionUtil'
import PubSub from 'pubsub-js'

export default {
  data() {
    return {
      routes: routes.filter(r => r.menu),
      username: (SessionUtil.get('currentUser') || {}).username,
      loggedIn:  SessionUtil.getAuth() ? true : false
    }
  },
  created() {
    PubSub.subscribe('refresh-menu', () => {
      this.routes = routes.filter(r => r.menu);
      this.username = SessionUtil.get('currentUser').username;
      this.loggedIn =  SessionUtil.getAuth() ? true : false;
    });

    PubSub.subscribe('goto-home', () => this.logout())
  },
  methods: {
    logout() {
      SessionUtil.remove('auth');
      SessionUtil.remove('current-user');
      PubSub.publish('refresh-menu')
      this.$router.push({ name: 'home' });
    }
  }
}
</script>

<style scoped>
  * {
    border-radius: 0!important;
  }
</style>
