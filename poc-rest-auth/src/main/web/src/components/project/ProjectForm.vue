<template>
  <div>
    <h1>Novo Projeto</h1>
    <form @submit.prevent="save()">
      <div class="form-group">
        <label>Nome</label>
        <input type="text" v-model="project.name" v-validate="'required'" data-vv-name="Nome" class="form-control">
        <span v-show="errors.has('Nome')" class="text-danger">{{ errors.first('Nome') }}</span>
      </div>

      <div class="text-right">
        <router-link :to="'/projects'" class="btn btn-default">Voltar</router-link>
        <button type="submit" class="btn btn-success">Salvar</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from '../../utils/axios'

export default {
  data() {
    return {
      project: {
        id: this.$route.params.id,
        name: null
      }
    }
  },
  methods: {
    save() {
      this.$validator.validateAll().then(success => {
        if (success) {
          if (this.project.id) {
            axios.put('/api/v1/projects', this.project)
              .then(response => {
                swal({ title: "Registro atualizado com sucesso.", type: "success" },
                  () => this.$router.push({ name: 'projectsIndex' }));
              })
          } else {
            axios.post('/api/v1/projects', this.project)
              .then(response => {
                swal({ title: "Registro inserido com sucesso.", type: "success" },
                  () => this.$router.push({ name: 'projectsIndex' }));
              })
          }
        }
      })

    }
  },
  created() {
    if (this.project.id) {
      axios.get(`/api/v1/projects/${this.project.id}`)
        .then(response => this.project = response.data)
    }
  }
}
</script>
