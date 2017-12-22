<template>
  <div>
    <form @submit.prevent="filter">
      <div class="panel panel-default">
        <div class="panel-heading">
          Filtro
        </div>
        <div class="panel-body">
          <div class="form-group">
            <label>Nome</label>
            <input type="text" v-model="project.name" class="form-control">
          </div>
        </div>
        <div class="panel-footer text-right">
          <router-link :to="'/projects/new'" class="btn btn-primary">Novo projeto</router-link>
          <button type="submit" class="btn btn-success">Filtrar</button>
        </div>
      </div>
    </form>

    <div class="panel panel-default">
      <div class="panel-heading">
        Projetos ({{projects.totalElements}})
      </div>
      <div class="panel-body">
        <div class="panel panel-default">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="project in projects.content">
                <td>{{project.id}}</td>
                <td>{{project.name}}</td>
                <td>
                  <a href="#" @click.prevent="remove(project.id)" class="btn btn-xs btn-danger">Remover</a>

                  <router-link :to="{ name: 'updateProjectForm', params: { id: project.id } }" class="btn btn-xs btn-default">
                    Editar
                  </router-link>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="panel-footer">
        <div class="text-right">
          <ul class="pagination">
            <li>
              <a href="#" @click.prevent="page(projects.number)">&lt;</a>
            </li>
            <li v-for="i in (0, projects.totalPages)">
              <a href="#" @click.prevent="page(i)">{{i}}</a>
            </li>
            <li>
              <a href="#" @click.prevent="page(projects.number + 2)">&gt;</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../../utils/axios'
export default {
  data() {
    return {
      projects: [],
      project: {
        name: null
      }
    }
  },
  created() {
    axios.get('/api/v1/projects')
      .then(response => this.projects = response.data);
  },
  methods: {
    page(i) {
      if (i < 0 || i > this.projects.totalPages) {
        return false;
      }
      axios.get(`/api/v1/projects?page=${i - 1}`, { params: this.project })
        .then(response => this.projects = response.data);
    },
    filter() {
      axios.get('/api/v1/projects', { params: this.project })
        .then(response => this.projects = response.data);
    },
    remove(id) {
      axios.delete(`/api/v1/projects/${id}`)
        .then(response => {
          swal({
            title: "Registro removido com sucesso.",
            type: "success"
          }, () => this.filter());
        })
    }
  }
}

</script>
