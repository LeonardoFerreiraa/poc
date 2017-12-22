<template>
  <div>
    <application-menu />
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import Menu from './components/shared/Menu.vue'

export default {
  name: 'app',
  components: {
    'application-menu': Menu
  },
  data () {
    return {
    }
  }
}
</script>
