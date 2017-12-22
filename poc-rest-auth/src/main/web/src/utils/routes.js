import Home from '../components/Home.vue'
import NotFound from '../components/NotFound.vue'
import Login from '../components/Login.vue'
import ProjectIndex from '../components/project/ProjectIndex.vue'
import ProjectForm from '../components/project/ProjectForm.vue'

export const routes = [
  { path: '', name: 'home', component: Home, menu: true, title: "Home" },
  { path: '*', name: 'notFound', component: NotFound, menu: false },

  { path: '/login', name: 'login', component: Login, menu: false },

  // project routes
  { path: '/projects', name: 'projectsIndex', component: ProjectIndex, menu: true, title: 'Projetos' },
  { path: '/projects/new', name: 'createProjectForm', component: ProjectForm, menu: false },
  { path: '/projects/edit/:id', name: 'updateProjectForm', component: ProjectForm, menu: false }
]
