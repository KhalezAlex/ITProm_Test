'use strict';

const emplApi = Vue.resource("/api/employees{/id}");


const getIndex = (list, id) => {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('option-gen', {
    props: ['gen'],
    template:
        '<option class="inputText" v-bind:value="gen.id">{{gen.name}}</option>'
})

Vue.component('form-empl', {
    props: ['employee', 'departments', 'professions', 'employees'],
    data: function() {
        return {
            id: '',
            fio: '',
            note: '',
            deptId: '',
            profId: ''
        }
    },
    watch: {
        employee: function(newValue) {
            this.id = newValue.id;
            this.fio = newValue.fio;
            this.note = newValue.note;
            this.deptId = newValue.deptId;
            this.profId = newValue.profId;
        }
    },
    template:
        '<form class="form">' +
            '<input type="text" class="inputText" placeholder="ФИО" v-model="fio">' +
            '<input type="text" class="inputText" placeholder="заметка" v-model="note">' +
            '<span>выбор отдела</span>' +
            '<select v-model="deptId">' +
                '<option-gen v-for="dept in departments" :key="dept.id" :gen="dept" />' +
            '</select>' +
            '<span>выбор профессии</span>' +
            '<select v-model="profId">' +
                '<option-gen v-for="prof in professions" :key="prof.id" :gen="prof" />' +
            '</select>' +
            '<input type="button" class="inputSubmit" value="save" @click="save"/>' +
        '</form>',
    methods: {
        save: function () {
            const employee = {fio: this.fio, note: this.note, deptId: this.deptId, profId: this.profId};
            if (this.id) {
                emplApi.update({id: this.id}, employee).then(result => {
                    result.json().then(data => {
                        const index = getIndex(this.employees, data.id);
                        this.employees.splice(index, 1, data);
                        this.flushData();
                    })
                })
            } else {
                emplApi.save({}, employee).then(result => {
                    result.json().then(data => {
                        this.employees.push(data);
                        this.flushData();
                    })
                })
            }
        },
        flushData: function () {
            this.id = '';
            this.fio = '';
            this.note = '';
            this.deptId = '';
            this.profId = '';
        }
    }
});

Vue.component('row-empl', {
    props: ['employee', 'employees', 'editParent', 'professions', 'departments'],
    template:
        '<tr>' +
            '<td>{{employee.id}}</td>' +
            '<td>{{employee.fio}}</td>' +
            '<td>{{getById(departments, employee.deptId)}}</td>' +
            '<td>{{getById(professions, employee.profId)}}</td>' +
            '<td>{{employee.note}}</td>' +
            '<td><input type="button" class="del_edit_button" value="edit" @click="edit"></td>' +
            '<td><input type="button" class="del_edit_button" value="delete" @click="del"></td>' +
        '</tr>',
    methods: {
        edit: function() {
            this.editParent(this.employee);
        },
        del: function() {
            emplApi.remove({id: this.employee.id}).then(result => {
                if (result.ok) {
                    this.employees.splice(this.employees.indexOf(this.employee), 1);
                }
            })
        },
        getById: function(collection, id) {
            return collection.filter(e => e.id === id)[0].name;
        }
    }
})

Vue.component('table-empl', {
    props: ['employees', 'professions', 'departments', 'editParent'],
    template:
        '<table class="table">' +
            '<tr>' +
                '<th>id</th>' +
                '<th>ФИО</th>' +
                '<th>Отдел</th>' +
                '<th>Профессия</th>' +
                '<th>Заметка</th>' +
                '<th>edit</th>' +
                '<th>delete</th>' +
            '</tr>' +
        '<row-empl v-for="employee in employees" :key="employee.id" :employee="employee" :professions="professions"' +
                                    ' :departments="departments" :employees="employees" :editParent="editParent"/>' +
        '</table>'
});

Vue.component('page-empl', {
    props: ['employees', 'departments', 'professions'],
    data: function() {
        return {
            employee: null
        }
    },
    template:
        '<div class="container">' +
            '<div class="modal">' +
                '<h3>Employees</h3>' +
                '<form-empl :employee="employee" :departments="departments" :professions="professions" ' +
                                                            ':employees="employees"/>' +
                '<table-empl :employees="employees" :professions="professions" ' +
                                                            ':departments="departments" :editParent="edit"/>' +
                '<form class="form">' +
                    '<span>Поиск по id</span>' +
                    '<input type="number" class="inputText" id="id" placeholder="введите id для поиска">' +
                    '<input type="button" class="inputSubmit" value="поиск" @click="findById">' +
                '</form>' +
                '<a class="del_edit_button" href="/index.html">home</a>' +
        '</div>' +
        '</div>',
    created: function() {
        emplApi.get().then(result => {
            result.json().then(data => {
                data.employees.forEach(empl => this.employees.push(empl));
                data.professions.forEach(prof => this.professions.push(prof));
                data.departments.forEach(dept => this.departments.push(dept));
            })
        })
    },
    methods: {
        edit: function(employee) {
            this.employee = employee;
        },
        findById: function() {
            Vue.http.get("/api/employees/" + document.getElementById("id").value).then(response => {
                response.json().then(data => {
                    alert(`{id: ${data.id}; fio: ${data.fio}}`);
                })
            })
        },
    }
})


const app = new Vue({
    el: '#emplApp',
    template: '<page-empl :employees="employees" :departments="departments" :professions="professions"/>',
    data: {
        employees: [],
        departments: [],
        professions: []
    }
});