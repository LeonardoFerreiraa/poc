<template>
  <div class="col-md-4 col-md-offset-4">
    <div class="panel panel-default">
      <div class="panel-heading text-center">
        <h3>Login</h3>
      </div>
      <div class="panel-body">
        <form @submit.prevent="submit">
          <div class="form-group">
            <div class="input-group">
              <span class="input-group-addon">
                <i class="glyphicon glyphicon-user"></i>
              </span>
              <input type="text" v-model="username" class="form-control">
            </div>
          </div>

          <div class="form-group">
            <div class="input-group">
              <span class="input-group-addon">
                <i class="glyphicon glyphicon-lock"></i>
              </span>
              <input type="password" v-model="password" class="form-control">
            </div>
          </div>

          <div class="text-right">
            <button type="submit" class="btn btn-success">Entrar</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../utils/axios'
import {SessionUtil} from '../utils/SessionUtil';
import PubSub from 'pubsub-js'

export default {
  data() {
    return {
      username: null,
      password: null
    }
  },
  methods: {
    submit() {
      axios.post('/login', {
        username: this.username,
        password: this.password
      }).then(response => {
        SessionUtil.add('currentUser', response.data);
        SessionUtil.setAuth(response.data.accessToken);
        PubSub.publish('refresh-menu');
        this.$router.push({ name: 'home' });
      });
    }
  }
}
</script>

<style scoped>
  h3 {
    margin: 0
  }
</style>
