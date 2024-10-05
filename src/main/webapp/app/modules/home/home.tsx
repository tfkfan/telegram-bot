import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { testCall } from 'app/modules/home/parser.reducer';

export const Home = () => {
  const dispatch = useAppDispatch();

  const testParser = () => {
    dispatch(testCall());
  };
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <p className="head">
          <Translate contentKey="home.title">Welcome, Java Hipster!</Translate>
        </p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>

            <Button onClick={testParser}>Test parser</Button>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
            </Alert>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
