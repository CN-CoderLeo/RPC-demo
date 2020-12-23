package test;

import rpc.annotation.Service;
import rpc.api.ByeService;

@Service
public class ByeServiceImpl implements ByeService {
    @Override
    public String bye(String name) {
        return "bye " + name;
    }
}
